package org.libreSubsEngine.subtitleRepository.repository;

public class SHA1 {
	
	private final String sha1Hex;
	
	public SHA1(final String sha1Hex) {
		this.sha1Hex = sha1Hex;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SHA1))
			return false;
		SHA1 other = (SHA1) obj;
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
