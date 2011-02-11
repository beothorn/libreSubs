package libreSubs.libreSubsSite;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.libreSubsEngine.subtitleRepository.repository.SubtitlesRepositoryHandler;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath(path ="status")
public class StatusPage extends BasePage {

	public StatusPage() {;
		addSubtitlesListPrintForDebug();
	}

	private String getMemoryPrintForBytes(final long bytes){
		final int kiloByte = 1024;
		final int megaByte = kiloByte*1024;
		final int gigaByte = megaByte*1024;
		if(bytes>=gigaByte){
			final double sizeInGigabytes = (double)bytes/(double)gigaByte;
			return String.format("%.2f GB", sizeInGigabytes);
		}
		if(bytes>=megaByte){
			final double sizeInMegabytes = (double)bytes/(double)megaByte;
			return String.format("%.2f MB", sizeInMegabytes);
		}
		if(bytes>=kiloByte){
			final double sizeInKilobytes = (double)bytes/(double)kiloByte;
			return String.format("%.2f KB", sizeInKilobytes);
		}
		return bytes+" bytes";
	}
	
	private void addSubtitlesListPrintForDebug() {
		final SubtitlesRepositoryHandler subtitlesRepositoryHandler = WicketApplication
				.getSubtitlesRepositoryHandler();
		final String listSubtitles = subtitlesRepositoryHandler.listSubtitles();
		final int subtitlesQuantity = subtitlesRepositoryHandler.subtitlesQuantity();
		final long subtitlesRepoSize = subtitlesRepositoryHandler.subtitlesRepoSize();
		
		final Runtime runtime = Runtime.getRuntime();
		final long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) ;
		final long freeMemory = runtime.freeMemory();
		final long totalMemory = runtime.totalMemory();
		final long maxMemory = runtime.maxMemory();
		
		add(new MultiLineLabel("message","Used Memory: " + getMemoryPrintForBytes(usedMemory) +"\n"
				+ "Free Memory: "+ getMemoryPrintForBytes(freeMemory) +"\n"
				+ "Total Memory: "+ getMemoryPrintForBytes(totalMemory) +"\n"
				+ "Max Memory: "+ getMemoryPrintForBytes(maxMemory) +"\n"
				+ "Quantidade de legendas: "+subtitlesQuantity+"\n"
				+"Tamanho do repositório: "+getMemoryPrintForBytes(subtitlesRepoSize)+"\n"
				+" Legendas: \n"
				+ listSubtitles
		));
	}
}
