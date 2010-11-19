package org.libreSubsEngine.subtitleRepository.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class GitRepoHandler {
	
	private final Git git;
	
	public GitRepoHandler(final File parentDir) {
		final Repository repo;
		final File gitFolder = new File(parentDir,".git");
		try {
			repo = new RepositoryBuilder().setGitDir(gitFolder).build();
		} catch (final IOException e1) {
			throw new RuntimeException("Error loading git repo", e1);
		}
		
		if(!gitFolder.exists()){
			gitFolder.mkdir();
			try {
				repo.create();
			} catch (final IOException e) {
				throw new RuntimeException("Error creating git repo", e);
			}	
		}
		git = new Git(repo);
	}

	public void commit() {
		final AddCommand addCommand = git.add();
		addCommand.addFilepattern(".");
		try {
			addCommand.call();
		} catch (final Exception e) {
			//TODO: dangerous runtime
			throw new RuntimeException("Excecide", e);
		}
		final CommitCommand commitCommand = git.commit();
		commitCommand.setCommitter("Jhon", "jhon@doe.com");
		commitCommand.setMessage("Development phase commit");
		try {
			commitCommand.call();
		} catch (final Exception e) {
			//TODO: change all runtimes to log
			throw new RuntimeException("Excecide", e);
		}
		
	}
	
//	public String getLog(final File file){
//		final LogCommand log = git.log();
//		log.add(start)();
//		
//		return null;
//	}

}
