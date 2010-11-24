package org.libreSubsEngine.subtitleRepository.git;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitRepoHandler {
	
	private static final String DEFAULT_COMMIT_MESSAGE = "Anonymous commit";
	private static final String DEFAULT_EMAIL = "jhon@doe.com";
	private static final String DEFAULT_NAME = "Anon";
	private final Git git;
	private Repository repo;
	private final String parentDirPath;
	
	public GitRepoHandler(final File parentDir) {
		this.parentDirPath = parentDir.getAbsolutePath();
		final File gitFolder = new File(parentDir,".git");
		
		final boolean repositoryIsVersioned = gitFolder.exists(); 
		
		try {
			repo = new RepositoryBuilder().setGitDir(gitFolder).build();
		} catch (final IOException e1) {
			throw new RuntimeException("Error loading git repo", e1);
		}
		
		git = new Git(repo);
		
		if(!repositoryIsVersioned){
			initialCommit(repo, gitFolder);
		}
	}

	private void initialCommit(final Repository repo, final File gitFolder) {
		gitFolder.mkdir();
		try {
			repo.create();
			addAll();
			commitWith("LibreSubs", "no@email.com", "Initial commit");
		} catch (final IOException e) {
			throw new RuntimeException("Error creating git repo", e);
		}
	}
	
	private void addAll() {
		final String allFilesPattern = ".";
		addFile(allFilesPattern);
	}

	private void addFile(final String filePattern) {
		final AddCommand addCommand = git.add();
		addCommand.addFilepattern(filePattern);
		try {
			addCommand.call();
		} catch (final Exception e) {
			throw new RuntimeException("Error while adding all repository to initial commit.", e);
		}
	}

	public void commitAnonymously() {
		final String commiterName = DEFAULT_NAME;
		final String commiterEmail = DEFAULT_EMAIL;
		final String commitMessage = DEFAULT_COMMIT_MESSAGE;
		
		commitWith(commiterName, commiterEmail, commitMessage);		
	}

	private void ifFileIsNotVersionedCry(final String filePath) {
		if(!filePath.contains(parentDirPath)){
			throw new RuntimeException("File is not on repository file:"+filePath+" repo:"+parentDirPath);
		}
	}

	private void commitWith(final String commiterName, final String commiterEmail,
			final String commitMessage) {
		try{
			final CommitCommand commitCommand = git.commit();
			commitCommand.setAll(true).setCommitter(commiterName, commiterEmail).setMessage(commitMessage).call();
		} catch (final Exception e) {
			throw new RuntimeException("Error commiting file", e);
		}
	}

	public void addFile(final File file) {
		final String filePath = file.getAbsolutePath();
		ifFileIsNotVersionedCry(filePath);
		final String filePattern = filePath.replace(parentDirPath+"/", "");
		
		addFile(filePattern);
		commitWith(DEFAULT_NAME, DEFAULT_EMAIL, "Added file "+file.getName());
	}
	
	public String getLog(final int lastNCommits){
		final StringBuffer stringBuffer = new StringBuffer();
		Iterable<RevCommit> call;
		try {
			call = git.log().call();
			int i = 0;
			for (final RevCommit revCommit : call) {
				final PersonIdent committerIdent = revCommit.getCommitterIdent();
				final String commiterName = committerIdent.getName();
				final int commitTime = revCommit.getCommitTime();
				final long milissecondsSinceEpoch = (commitTime)*1000L;
				final Date commitDate = new Date(milissecondsSinceEpoch);
				final String shortMessage = revCommit.getShortMessage();
				final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm");
				final String formatedDate = formatter.format(commitDate);
				stringBuffer.append(commiterName+" "+formatedDate+" "+shortMessage+"\n");
				i++;
				if(i>=lastNCommits){
					return stringBuffer.toString();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

}
