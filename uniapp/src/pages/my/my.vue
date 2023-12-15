<script setup lang="ts">
import { useUserStore } from '@/stores';
import Setting from './components/setting/setting.vue';
import { http, upLoad } from '@/utils/http';
import { checkFile } from '@/utils/checkFile';
import { onLoad } from '@dcloudio/uni-app';
import { getUserInfoAPI } from '@/services/user';
// 获取屏幕边界到安全区域距离
const { safeAreaInsets } = uni.getSystemInfoSync()
const userStore = useUserStore();
// 修改头像
const onPhotoChange = () => {
  // 调用拍照/选择图片
  // 选择图片条件编译
  // #ifdef H5 || APP-PLUS
  // 微信小程序从基础库 2.21.0 开始， wx.chooseImage 停止维护，请使用 uni.chooseMedia 代替
  uni.chooseImage({
    count: 1,
    success: (res) => {
      // 文件路径
      const tempFilePaths = res.tempFilePaths
      // 上传
      uploadPhoto(tempFilePaths[0])
    },
  })
  // #endif
  // #ifdef MP-WEIXIN
  // uni.chooseMedia 仅支持微信小程序端
  uni.chooseMedia({
    // 文件个数
    count: 1,
    // 文件类型
    mediaType: ['image'],
    success: (res) => {
      // 本地路径
      const { tempFilePath } = res.tempFiles[0]
      // 上传
      uploadPhoto(tempFilePath)
    },
  })
  // #endif
}
// 文件上传-兼容小程序端、H5端、App端
const uploadPhoto = async (file: string) => {
  console.log(file);
  const res = await upLoad({
    url: '/user/updatePhoto',
    name: 'file',
    filePath: file
  })
  if(res.code == 0){
    const vlaue = res.result as string;
    userStore.profile!.photo! = vlaue;
  }
}
const getUserInfo = async ()=>{
  const data = await getUserInfoAPI();
  const old = userStore.profile;
  userStore.setProfile({...data.result,token:old?.token});
}
onLoad(()=>{
  getUserInfo();
})
</script>

<template>
  <scroll-view class="viewport" scroll-y enable-back-to-top @scrolltolower="">
   <!-- 个人资料 -->
   <view class="profile" :style="{ paddingTop: safeAreaInsets!.top + 'px' }">
      <!-- 情况1：已登录 -->
      <view class="overview" v-if="true">
        <navigator url="/pagesMember/profile/profile" hover-class="none">
          <image class="avatar" :src="checkFile(userStore.profile?.photo)" mode="aspectFill"></image>
        </navigator>
        <view class="meta">
          <view class="nickname">
            {{ userStore.profile?.username }}
          </view>
          <view class="extra"  hover-class="none" @click="onPhotoChange">
            <text class="update">更新头像昵称</text>
          </view>
        </view>
      </view>
      <!-- 情况2：未登录 -->
      <view class="overview" v-else>  </view>
    </view>
    <!-- 设置列表 -->
    <view class="settings">
      <Setting/>
    </view>
  </scroll-view>
</template>

<style lang="scss">
page {
  height: 100%;
  overflow: hidden;
  background-color: #f4f4f4;
}

.viewport {
  height: 100%;
  background-repeat: no-repeat;
  background-image: url(https://pcapi-xiaotuxian-front-devtest.itheima.net/miniapp/images/center_bg.png);
  background-size: 100% auto;
}

/* 用户信息 */
.profile {
  margin-top: 20rpx;
  position: relative;

  .overview {
    display: flex;
    height: 120rpx;
    padding: 0 36rpx;
    color: #fff;
  }

  .avatar {
    width: 120rpx;
    height: 120rpx;
    border-radius: 50%;
    background-color: #eee;
  }

  .gray {
    filter: grayscale(100%);
  }

  .meta {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    line-height: 30rpx;
    padding: 16rpx 0;
    margin-left: 20rpx;
  }

  .nickname {
    max-width: 350rpx;
    margin-bottom: 16rpx;
    font-size: 30rpx;

    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .extra {
    display: flex;
    font-size: 20rpx;
  }

  .tips {
    font-size: 22rpx;
  }

  .update {
    padding: 3rpx 10rpx 1rpx;
    color: rgba(255, 255, 255, 0.8);
    border: 1rpx solid rgba(255, 255, 255, 0.8);
    margin-right: 10rpx;
    border-radius: 30rpx;
  }


}

.orders {
  position: relative;
  z-index: 99;
  padding: 30rpx;
  margin: 50rpx 20rpx 0;
  background-color: #fff;
  border-radius: 10rpx;
  box-shadow: 0 4rpx 6rpx rgba(240, 240, 240, 0.6);

  .title {
    height: 40rpx;
    line-height: 40rpx;
    font-size: 28rpx;
    color: #1e1e1e;

    .navigator {
      font-size: 24rpx;
      color: #939393;
      float: right;
    }
  }

  .section {
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding: 40rpx 20rpx 10rpx;
    .navigator,
    .contact {
      text-align: center;
      font-size: 24rpx;
      color: #333;
      &::before {
        display: block;
        font-size: 60rpx;
        color: #ff9545;
      }
    }
    .contact {
      padding: 0;
      margin: 0;
      border: 0;
      background-color: transparent;
      line-height: inherit;
    }
  }
}
.settings {
  background-color: #f7f7f8;
  margin-top: 100rpx;
}
</style>