package user.management.vn.service;

import user.management.vn.entity.TokenVerifition;

public interface TokenVerificationService {
	TokenVerifition addToken(TokenVerifition tokenVerifition);
	TokenVerifition editToken(TokenVerifition tokenVerifition);
	boolean deleteTokenById(Long id);
	TokenVerifition findTokenByTokenCode(String token);
}
