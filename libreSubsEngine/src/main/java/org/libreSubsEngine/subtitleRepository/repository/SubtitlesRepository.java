package org.libreSubsEngine.subtitleRepository.repository;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.libreSubsCommons.Language;
import org.libreSubsEngine.subtitleRepository.SubtitleRepositoryLocation;
import org.libreSubsEngine.subtitleRepository.fileUtils.RepositoryScanner;
import org.libreSubsEngine.subtitleRepository.fileUtils.RepositoryScannerListener;
import org.libreSubsEngine.subtitleRepository.git.GitRepoHandler;

public class SubtitlesRepository implements RepositoryScannerListener{

	private final Map<SubtitleKey, Subtitle> subtitles;
	private final SubtitleRepositoryLocation repositoryLocation;
	private final GitRepoHandler gitRepoHandler;
	
	public SubtitlesRepository(final SubtitleRepositoryLocation repoLocation){		
		this.repositoryLocation = repoLocation;
		subtitles = new LinkedHashMap<SubtitleKey, Subtitle>();
		final File repoDir = repoLocation.getBaseDir();
		try {
			RepositoryScanner.scan(this, repoDir);
		} catch (final IOException e) {
			throw new RuntimeException("Error loading reposiory",e);
		}
		gitRepoHandler = new GitRepoHandler(repoDir);
	}

	void addSubtitle(final PartialSHA1 videoID, final Language language,final String content) throws IOException {
		final String videoIDAsString = videoID.toString();
		final String strDirName = videoIDAsString.substring(0, 2);
		final File srtDir = new File(repositoryLocation.getBaseDir(), strDirName);
		if(!srtDir.exists()){
			srtDir.mkdir();
		}
		final String fileName = videoIDAsString + "." + language;
		final File subtitleFile = new File(srtDir, fileName);
		if(subtitleFile.exists())
			throw new IOException("File already exists");
		else
			subtitleFile.createNewFile();
		
		FileUtils.writeStringToFile(subtitleFile, content);
		gitRepoHandler.addFile(subtitleFile);
		loadSubtitleFromRepositoryDecomposeName(subtitleFile);
	}
	
	private void loadSubtitleFromRepositoryDecomposeName(final File srtFile) throws IOException {
		final String videoID = StringUtils.substringBeforeLast(srtFile.getName(), ".");
		final String language = StringUtils.substringAfterLast(srtFile.getName(), ".");
		final PartialSHA1 videoSHA1 = new PartialSHA1(videoID);
		loadSubtitleFromRepository(videoSHA1, language, srtFile);
	}

	private void loadSubtitleFromRepository(final PartialSHA1 videoSHA1,final String language,final File srtFile
			) throws IOException {
		final String srtFileContent = FileUtils.readFileToString(srtFile);
		final SubtitleKey subtitleKey = new SubtitleKey(Language.valueOf(language), videoSHA1);
		final Subtitle subtitle = new Subtitle(srtFileContent, srtFile);
		subtitles.put(subtitleKey, subtitle);
	}

	public String getSubtitleContentsFromVideoIDAndLanguageOrNull(final Language language,
			final PartialSHA1 videoID) throws IOException {
		final SubtitleKey subtitleKey = new SubtitleKey(language, videoID);
		return getSubtitleContentsForKeyOrNull(subtitleKey);
	}

	public String getSubtitleContentsForKeyOrNull(final SubtitleKey subtitleKey){
		if(subtitleKey == null)
			return null;
		final Subtitle subtitle = subtitles.get(subtitleKey);
		if (subtitle == null)
			return null;
		final String content = subtitle.getContent();
		return content;
	}

	public void changeContentsForSubtitle(final String newContent, final Language language,
			final PartialSHA1 videoID) throws IOException {
		changeContentsForSubtitle(newContent, new SubtitleKey(language, videoID) );
	}
	
	public void changeContentsForSubtitle(final String newContent, final SubtitleKey subtitleKey) throws IOException {
		final Subtitle subtitle = subtitles.get(subtitleKey);
		subtitle.setContent(newContent);
		commitAnonymously();
	}

	public String listSubtitles() {
		final StringBuilder subtitlesList = new StringBuilder();
		final Set<SubtitleKey> keySet = subtitles.keySet();
		for (final SubtitleKey subtitleKey : keySet) {
			subtitlesList.append(subtitleKey.toString()+"\n");
		}
		return subtitlesList.toString();
	}

	public File getBaseDir() {
		return repositoryLocation.getBaseDir();
	}

	public boolean subtitleExists(final SubtitleKey key){
		if(key == null)
			return false;
		return subtitles.containsKey(key);
	}

	public void commitAnonymously() {
		gitRepoHandler.commitAnonymously();
	}

	@Override
	public void addFile(final File child) {
		try {
			loadSubtitleFromRepositoryDecomposeName(child);
		} catch (final IOException e) {
			throw new RuntimeException("Error opening file from reposiory",e);
		}
	}

}
