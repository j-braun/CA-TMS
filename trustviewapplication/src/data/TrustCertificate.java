package data;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;
import javax.xml.bind.DatatypeConverter;

public class TrustCertificate {
	private final String serial;
	private final String issuer;
	private final String subject;
	private final String publicKey;
	private final Certificate certificate;

	public TrustCertificate(String serial, String issuer, String subject,
			String publicKey) {
		this.serial = serial;
		this.issuer = issuer;
		this.subject = subject;
		this.publicKey = publicKey;
		this.certificate = null;
	}

	public TrustCertificate(Certificate certificate) {
		if (certificate instanceof X509Certificate) {
			X509Certificate x509cert = (X509Certificate) certificate;

			this.serial = x509cert.getSerialNumber().toString();
			this.issuer = x509cert.getIssuerX500Principal().getName(
					X500Principal.CANONICAL);
			this.subject = x509cert.getSubjectX500Principal().getName(
					X500Principal.CANONICAL);
			this.publicKey = DatatypeConverter.printBase64Binary(
					x509cert.getPublicKey().getEncoded());
			this.certificate = x509cert;
		}
		else
			throw new UnsupportedOperationException(
					"Cannot create a TrustCertificate from a " +
					certificate.getClass().getSimpleName());
	}

	public String getSerial() {
		return serial;
	}

	public String getIssuer() {
		return issuer;
	}

	public String getSubject() {
		return subject;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	@Override
	public int hashCode() {
		return 29791 * serial.hashCode() + 961 * issuer.hashCode() +
				31 * subject.hashCode() + publicKey.hashCode();
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
		return serial.equals(other.getSerial()) &&
		       issuer.equals(other.getIssuer()) &&
		       subject.equals(other.getSubject()) &&
		       publicKey.equals(other.getPublicKey());
	}
}
