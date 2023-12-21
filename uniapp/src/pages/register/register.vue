<script setup lang="ts">
import { postRegisterAPI } from '@/services/user';
import { ref } from 'vue';
import type { UniFormsInstance } from '@uni-helper/uni-ui-types';
/** 用户登录信息 */
const form = ref<{
  /** 用户名 */
  account: string;
  /** 密码 */
  password: string;
  /** 确认密码 */
  rePassword: string;
}>({ account: '', password: '', rePassword: '' });

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
  rePassword:
  {
    rules: [{ required: true, errorMessage: '请填写密码' },
    { pattern: /^[^\s]{5,16}$/, errorMessage: '请输入5到16位字符（不包括空格）' }
    ],
  },
}
const formRef = ref<UniFormsInstance>();
const onSubmit = async () => {
  try {
    await formRef.value.validate();
    const res = await postRegisterAPI({
      username: form.value.account,
      pwd: form.value.password,
      re_pwd: form.value.rePassword
    });
    if (res.code == 0) {
      uni.showToast({
        icon: 'success',
        title: '注册成功'
      })
      setTimeout(() => {
        uni.hideTabBar();
        uni.navigateTo({
          url: '/pages/login/login',
        })
      }, 500);
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
</script>
<template>
  <view class="viewport">
    <view class="logo">
      <image src="@/static/images/logo.jpg"></image>
    </view>
    <view class="register">
      <!-- 网页端表单注册 -->
      <uni-forms ref="formRef" :rules="rules" :model="form">
        <uni-forms-item name="account">
          <input v-model="form.account" class="input" placeholder="请输入用户名/手机号码" />
        </uni-forms-item>
        <uni-forms-item name="password">
          <input v-model="form.password" class="input" password placeholder="请输入密码" />
        </uni-forms-item>
        <uni-forms-item name="rePassword">
          <input v-model="form.rePassword" class="input" password placeholder="再次输入密码" />
        </uni-forms-item>
        <button @tap="onSubmit" class="button phone">注册</button>
      </uni-forms>
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

.register {
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
