import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author LY
 * @create 2021-02-07 3:55
 */
public class Test {

    @Resource
    UcenterMemberService ucenterMemberService;

    @org.junit.Test
    public void test02(){
        String memberId = JwtUtils.getMemberIdByJwtTokenStr("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2MTI2Mzk2MjgsImV4cCI6MTYxMjcyNjAyOCwiaWQiOiIxMzU2NzgyNjY3MDg5MDc2MjI1Iiwibmlja25hbWUiOiLpvo3pvpbpvpgifQ.PPoZiS0VFP6TNCYsJ1pIlFu_OqUZS_FFxkN98AuO6YU");
        UcenterMember member = ucenterMemberService.getById("1");
        System.out.println(member);
    }

}
