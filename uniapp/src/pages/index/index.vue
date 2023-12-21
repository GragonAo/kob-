<template>
  <view v-if="gameStore.gameState === GameState.Game">
    <Game />
  </view>
  <view v-else>
    <view class="user-info">
    <view class="avatar-container">
      <image class="info-image" :src="userStore.profile?.photo" />
      <image class="icon-image" src="../../static/images/gold.png" />
    </view>
    <text class="info-name">{{ userStore.profile?.username }}</text>
  </view>
  <view class="content" :style="{ height: `calc(100vh - ${safeAreaInsets?.top}px - ${safeAreaInsets?.bottom}px - 180rpx)`, paddingBottom: safeAreaInsets?.bottom + 'px' }">
    <view class="matching-block">
      <Matching />
    </view>
    <view class="more-actions">
      <navigator @click="test">
        <image class="icon" src="../../static/images/ai.png"></image>
        <text class="text">练习赛</text>
      </navigator>
      <navigator url="/pages/raking/raking">
        <image class="icon" src="../../static/images/rating.png"></image>
        <text class="text">排行榜</text>
      </navigator>
      <navigator url="/pages/battleRecord/battleRecord">
        <image class="icon" src="../../static/images/vs.png"></image>
        <text class="text">对战记录</text>
      </navigator>
    </view>
  </view>
  </view>
  
</template>

<script setup lang='ts'>
import Matching from './components/matching/matching.vue';
import { onShow, onHide } from '@dcloudio/uni-app';
import { ref } from 'vue';
import { connectWebSocket } from '@/utils/connectWebSocket';
import { useGameStore } from '@/stores/modules/game';
import { useUserStore } from '@/stores';
import { GameState } from '@/enums/game';
import  Game from './components/Game/Game.vue';
import { getUserInfoAPI } from '@/services/user';
const { safeAreaInsets } = uni.getSystemInfoSync()
const gameStore = useGameStore();
const userStore = useUserStore();
const wbSocket = ref<connectWebSocket>();
const test = ()=>{
  uni.showToast({
    title:'功能暂未开放,敬请期待！',
    icon:'none'
  })
}
const getUserInfo = async ()=>{
  const data = await getUserInfoAPI();
  const old = userStore.profile;
  userStore.setProfile({...data.result,token:old?.token});
}
const matchingRef = ref();
//页面显示时
onShow(() => {
  getUserInfo();
  wbSocket.value = new connectWebSocket();
  wbSocket.value.socketTask();
  gameStore.initGameStore();

});
onHide(() => {
  wbSocket.value?.closeSocket();
  gameStore.clearGameState();
})
</script>

<style lang='scss' scoped>
.page {
  width: 100%;
  height: 100%;
}

.user-info {
  width: 100%;
  height: auto;
  display: flex;
  align-items: center;
  background-color: rgb(99, 139, 215);

  .avatar-container {
    display: flex;
    align-items: flex-end;
    margin: 30rpx 0;
    padding-left: 20rpx;
    position: relative;

    .info-image {
      width: 150rpx;
      height: 150rpx;
      border-radius: 50%;
      border: 8rpx solid rgb(244, 205, 139);
    }

    .icon-image {
      position: absolute;
      bottom: 0;
      right: 0;
      width: 50rpx;
      height: 50rpx;
      border-radius: 50%;
      margin-bottom: 5rpx;
      margin-right: 5rpx;
    }
  }

  .info-name {
    color: white;
    margin-top: 10rpx;
    font-size: 200rpx;
    margin-left: 10rpx;
    padding-left: 20rpx;
  }
}

.content {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 50rpx 0;
  background: rgb(128, 173, 247);
}

.matching-block {
  flex: 3;
  background-color: rgb(255, 255, 255);
  width: 90%;
  margin-top: 50rpx;
  border-radius: 30rpx;
  display: flex;
  flex-direction: column;
}

.more-actions {
  display: flex;
  justify-content: space-between;
  width: 90%;
  margin-top: 50rpx;

  navigator {
    width: 200rpx;
    height: 200rpx;
    border-radius: 10rpx; /* Adjust as needed */
    background-color: rgb(112, 163, 243);
    color: rgb(255, 255, 255);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    /* Styles for the icon (adjust as needed) */
    .icon {
      margin-top: 20rpx;
      width: 130rpx; /* Adjust the size of the icon */
      height: 100rpx; /* Adjust the size of the icon */
    }

    /* Styles for the text (adjust as needed) */
    .text {
      margin-top: 10rpx; /* Adjust spacing between icon and text */
      font-size: 20rpx; /* Adjust the font size of the text */
    }
  }
}

/* Media queries for responsiveness */
@media screen and (max-width: 600px) {
  .content {
    padding: 20rpx 0;
  }

  .matching-block {
    margin-top: 20rpx;
  }

  .more-actions {
  display: flex;
  justify-content: space-between;
  width: 90%;
  margin-top: 20rpx;
}

.more-actions navigator {
  flex: 1; /* Each navigator takes equal width */
  margin-right: 10rpx; /* Adjust the spacing between navigators */
}

  .user-info {
    .avatar-container {
      margin: 10rpx 0;
    }

    .info-name {
      margin-top: 5rpx;
      font-size: 30rpx;
    }
  }
}
</style>

