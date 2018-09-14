package service.account;

/**
 * 账户服务。
 * <p>
 * 1. 注册。有两种方式：游客账户注册，普通账户注册。
 * <p>
 * TODO 游客只是临时账号，服务器定期删除离线超过 1 个星期的临时账号。
 * <p>
 * 注意：游客账户可以转回普通账户（重新填写账号密码），进而拥有完整的权限。
 * <p>
 * 2. 登陆。通过账号和密码登录，生成 Token。注册也可以获得令牌。
 * <p>
 * 令牌相关：通过 accessToken 读取用户信息，修改基本资料。通过 refreshToken 延长访问令牌的有效期。
 * <p>
 * 注意：一旦涉及敏感操作，必须再次验证账户密码，以识别为账户所有人。
 * <p>
 * 访问令牌：以 token:*access_token* 为 Key，以 账号 ID 为 Value，存储 Redis 中，设 30 天过期。
 * <p>
 * 刷新令牌：以 account:id 为 Hash，以 token 为 Key，以刷新令牌为 Value，检测通过才更新令牌。
 *
 * @author qiang.zhang
 */
public interface AccountService {
}
