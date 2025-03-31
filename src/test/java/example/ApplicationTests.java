package example;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

	@Test
	void bcryptPassword() {
		PasswordEncoder bcrypt = new UnsafeTruncatingPasswordEncoder(new BCryptPasswordEncoder());

		String maxByteRawPassword = "12345678".repeat(9); // 8 * 9 = 72 chars (each is byte)
		assertThat(maxByteRawPassword.getBytes(StandardCharsets.UTF_8)).hasSize(72);
		String exceedMaxByteRawPassword = maxByteRawPassword + "1";
		assertThat(exceedMaxByteRawPassword.getBytes(StandardCharsets.UTF_8)).hasSizeGreaterThan(72);

		String exceedMaxBcrypt = "$2a$10$WbprTaPG.Cf5PYRu5aj0cOj.zJN.QbwIc4lKu6cguMdM3GADCOsFm";bcrypt.encode(exceedMaxByteRawPassword);
		System.out.println(exceedMaxBcrypt);

		assertThat(bcrypt.matches(maxByteRawPassword, exceedMaxBcrypt)).isTrue();
	}

	static class UnsafeTruncatingPasswordEncoder implements PasswordEncoder {

		private final PasswordEncoder delegate;

		UnsafeTruncatingPasswordEncoder(PasswordEncoder delegate) {
			this.delegate = delegate;
		}

		@Override
		public String encode(CharSequence rawPassword) {
			CharSequence truncatedRawPassword = truncate(rawPassword);
			return delegate.encode(truncatedRawPassword);
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			CharSequence truncatedRawPassword = truncate(rawPassword);
			return delegate.matches(truncatedRawPassword, encodedPassword);
		}

		@Override
		public boolean upgradeEncoding(String encodedPassword) {
			return delegate.upgradeEncoding(encodedPassword);
		}

		CharSequence truncate(CharSequence rawPassword) {
			final Charset utf8 = StandardCharsets.UTF_8;
			if (rawPassword == null) {
				return null;
			}
			byte[] bytes = rawPassword.toString().getBytes(utf8);
			if (bytes.length <= 72) {
				return rawPassword;
			} else {
				return new String(bytes, 0, 72, utf8);
			}
		}
	}
}