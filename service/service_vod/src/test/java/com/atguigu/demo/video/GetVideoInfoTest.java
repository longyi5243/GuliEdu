package com.atguigu.demo.video;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @author LY
 * @create 2021-01-24 18:43
 */
public class GetVideoInfoTest {

    /**
     * 获取视频播放地址
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        //创建初始化对象
        DefaultAcsClient client = VideoUtils.initVodClient("LTAI4GD4TrMHGVFwJExmQVdH", "pjBPnoFSEyBxk5G6VyYdIR0jnf3FNW");

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象中设置视频id
        request.setVideoId("87c3c2c5f31a4886b7280c632ecf01d8");

        //调用初始化对象里的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        //结果集合
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        //获取播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    /**
     * 获取视频凭证
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {
        //创建初始化对象
        DefaultAcsClient client = VideoUtils.initVodClient("LTAI4GD4TrMHGVFwJExmQVdH", "pjBPnoFSEyBxk5G6VyYdIR0jnf3FNW");

        //创建获取视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request对象中设置视频id
        request.setVideoId("37e3b4250b2943a88bb3b1cdd12edf56");

        //调用初始化对象里的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        //获取播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }

}
