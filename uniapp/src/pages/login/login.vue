<script setup lang="ts">
import { getUserInfoAPI, postLoginWebeAPI } from '@/services/user';
import { useUserStore } from '@/stores';
import { ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import type { UniFormsInstance } from '@uni-helper/uni-ui-types';
import  privacyPopup  from "../components/PrivacyModal/PrivacyModal.vue";
/** 用户登录信息 */
const form = ref<{
  /** 用户名 */
  account: string;
  /** 密码 */
  password: string
}>({ account: '', password: '' });
/** 表单校验规则 */
const rules: UniHelper.UniFormsRules = {
  account:
  {
    rules: [{ required: true, errorMessage: '请填写用户名' },
    { pattern: /^[^\s]{5,16}$/, errorMessage: '请输入5到16位字符（不包括空格）' }
    ],

  },
  password:
  {
    rules: [{ required: true, errorMessage: '请填写密码' },
    { pattern: /^[^\s]{5,16}$/, errorMessage: '请输入5到16位字符（不包括空格）' }
    ],
  },
}
const formRef = ref<UniFormsInstance>();
const userStore = useUserStore();
const onSubmit = async () => {
  try {
    await formRef.value.validate();
    const res = await postLoginWebeAPI(form.value.account, form.value.password);
    if (res.code == 0) {
      userStore.setProfile({ token: res.result });
      const data = await getUserInfoAPI();
      userStore.setProfile({ ...data.result, token: res.result });
      uni.showToast({
        icon: 'success',
        title: '登录成功',
      });
      setTimeout(() => {
        uni.hideToast();
        uni.switchTab({
          url: '/pages/index/index',
        })
      }, 1000);
    } else {
      uni.showToast({
        icon: 'error',
        title: res.msg
      });
    }
  } catch (error) {
    uni.showToast({
      icon: 'error',
      title: '请按要求填写',
    });
  }

}
onLoad(() => {
  if (userStore.profile?.token) {
    uni.switchTab({
      url: '/pages/index/index',
    })
  }
})
</script>
<template>
  <view class="viewport">
    <view class="logo">
      <image src="@/static/images/logo.jpg"></image>
    </view>
    <view class="login">
      <!-- 网页端表单登录 -->
      <uni-forms ref="formRef" :rules="rules" :model="form">
        <uni-forms-item name="account">
          <input v-model="form.account" class="input" placeholder="请输入用户名/手机号码" />
        </uni-forms-item>
        <uni-forms-item name="password">
          <input v-model="form.password" class="input" password placeholder="请输入密码" />
        </uni-forms-item>
        <button @tap="onSubmit" class="button phone">登录</button>
      </uni-forms>


      <!-- 小程序端授权登录 -->
      <!-- #ifdef MP-WEIXIN -->
      <!-- <button class="button phone" open-type="getPhoneNumber" @getphonenumber="">
        <text class="icon icon-phone"></text>
        手机号快捷登录
      </button> -->
      <!-- #endif -->
      <view class="extra">
        <view class="caption">
          <text>其他登录方式</text>
        </view>
        <view class="options">
          <navigator class="icon icon-phone" url="../register/register">立即注册</navigator>
        </view>
      </view>
      <!-- <privacyPopup></privacyPopup> -->
      <!-- #ifdef MP-WEIXIN -->
      <!-- 隐私协议弹窗 -->
      
      <!-- #endif -->
      <view class="tips">欢迎使用蛇蛇作战</view>
    </view>
  </view>
</template>

<style lang="scss">
page {
  height: 100%;
}

.viewport {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20rpx 40rpx;
}

.logo {
  flex: 1;
  text-align: center;

  image {
    width: 220rpx;
    height: 220rpx;
    margin-top: 15vh;
  }
}

.login {
  display: flex;
  flex-direction: column;
  height: 60vh;
  padding: 40rpx 20rpx 20rpx;

  .input {
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    border: 1px solid #ddd;
    padding-left: 30rpx;
    margin-bottom: 20rpx;
  }

  .button {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 80rpx;
    font-size: 28rpx;
    border-radius: 72rpx;
    color: #fff;

    .icon {
      font-size: 40rpx;
      margin-right: 6rpx;
    }
  }

  .phone {
    background-color: #28bb9c;
  }

  .wechat {
    background-color: #06c05f;
  }

  .extra {
    flex: 1;
    padding: 70rpx 70rpx 0;

    .caption {
      width: 440rpx;
      line-height: 1;
      border-top: 1rpx solid #ddd;
      font-size: 26rpx;
      color: #999;
      position: relative;

      text {
        transform: translate(-40%);
        background-color: #fff;
        position: absolute;
        top: -12rpx;
        left: 50%;
      }
    }

    .options {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 70rpx;

      button {
        padding: 0;
        background-color: transparent;

        &::after {
          border: none;
        }
      }
    }

    .icon {
      font-size: 24rpx;
      color: #444;
      display: flex;
      flex-direction: column;
      align-items: center;

      &::before {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 80rpx;
        height: 80rpx;
        margin-bottom: 6rpx;
        font-size: 40rpx;
        border: 1rpx solid #444;
        border-radius: 50%;
      }
    }

    .icon-weixin::before {
      border-color: #06c05f;
      color: #06c05f;
    }
  }
}

.tips {
  position: absolute;
  bottom: 80rpx;
  left: 20rpx;
  right: 20rpx;
  font-size: 22rpx;
  color: #999;
  text-align: center;
}
</style>
