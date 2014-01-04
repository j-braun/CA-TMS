package data;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public abstract class TrustCertificate {
	public abstract Principal getIssuer();
	public abstract Principal getSubject();
	public abstract PublicKey getPublicKey();

	public static TrustCertificate fromCertificate(Certificate cert) {
		if (cert instanceof X509Certificate)
			return new X509TrustCertificate((X509Certificate) cert);

		throw new ClassCastException("Cannot create a TrustCertificate from a " +
		                             cert.getClass().getSimpleName());
	}

	private static class X509TrustCertificate extends TrustCertificate {
		private final Principal issuer;
		private final Principal subject;
		private final PublicKey publicKey;

		private X509TrustCertificate(X509Certificate cert) {
			issuer = cert.getIssuerDN();
			subject = cert.getSubjectDN();
			publicKey = cert.getPublicKey();
		}

		@Override
		public Principal getIssuer() {
			return issuer;
		}

		@Override
		public Principal getSubject() {
			return subject;
		}

		@Override
		public PublicKey getPublicKey() {
			return publicKey;
		}

		@Override
		public int hashCode() {
			return 961 * issuer.hashCode() + 31 * subject.hashCode() +
					publicKey.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TrustCertificate other = (TrustCertificate) obj;
			return issuer.equals(other.getIssuer()) &&
			       subject.equals(other.getSubject()) &&
			       publicKey.equals(other.getPublicKey());
		}
	}
}
