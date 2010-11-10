package org.libreSubsEngine.subtitleRepository.repository;

public class PartialSHA1 {
	
	private final String sha1Hex;
	
	public PartialSHA1(final String sha1Hex) {
		this.sha1Hex = sha1Hex;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(!(obj instanceof PartialSHA1))
			return false;
		final PartialSHA1 other = (PartialSHA1) obj;
		return sha1Hex.equals(other.sha1Hex);
	}
	
	@Override
	public int hashCode() {
		return sha1Hex.hashCode();
	}

	@Override
	public String toString() {
		return sha1Hex;
	}
}
