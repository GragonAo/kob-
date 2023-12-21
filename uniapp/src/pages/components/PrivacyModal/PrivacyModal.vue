<template>
  <u-popup
    class="privacy-wrap"
    mode="center"
    v-show="isShow"
    round="10">
    <view class="content">
      <view class="title">隐私协议须知</view>
      <view class="tips">
        在使用本服务之前，请仔细阅读
        <text class="privacy-link" @click="handleReadPrivacy">
          {{ privacyContractName }}
        </text>
        。如果你同意{{ privacyContractName }}，请点击“同意”开始使用服务。
      </view>
      <view class="btns">
        <button class="btn cancel" @click="handleDisagreePrivacy">取消</button>
        <button
          class="btn cancel"
          @click="handleAgreePrivacy">
          同意
        </button>
      </view>
    </view>
  </u-popup>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores';
import { ref } from 'vue';


const userStore = useUserStore();
// 同意按钮id
const AGREE_ID = 'agree-btn';
const privacyContractName = ref('隐私保护协议');
const isShow = ref(true);
// 初始化隐私协议
initPrivacyInfo();

async function initPrivacyInfo() {
  isShow.value = true;
}

/**
 * 阅读隐私协议
 */
function handleReadPrivacy() {
  //uni.openPrivacyContract();
}

/**
 * 关闭弹窗
 */
function closeModal() {
  console.log("关闭页面");
  isShow.value = false;
}

/**
 * 拒绝隐私协议
 */
function handleDisagreePrivacy() {
  closeModal();
  console.log("用户拒绝了协议")
}

/**
 * 同意隐私协议
 */
function handleAgreePrivacy() {
  closeModal();
  console.log("用户同意了协议")
}
</script>

<style lang="scss" scoped>
.content {
  width: 600rpx;
  height: 300rpx;
  padding: 20rpx;
  border-radius: 10px;
}

.tips {
  margin-top: 16px;

  .privacy-link {
    color: #34a5fc;
  }
}

.btns {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 30px;

  .btn {
    width: 120px;
    height: 36px;
    background: var(--bg-color);
    text-align: center;
    line-height: 36px;
    color: #fff;
    border-radius: 20px;
    font-size: 16px;

    &.cancel {
      border: 1px solid var(--bg-color);
      color: var(--bg-color);
      background: #fff;
    }

    &:not(:last-child) {
      margin-right: 20px;
    }
  }
}
</style>
