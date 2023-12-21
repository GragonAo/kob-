<script setup lang="ts">
import { getRatingListAPI } from '@/services/raking';
import { useUserStore } from '@/stores/modules/user';
import { onLoad } from '@dcloudio/uni-app';
import type { RatingInfo } from '@/types/rating';
import { ref } from 'vue';
import { checkFile } from '@/utils/checkFile';
import { renderList } from 'vue';
const userStore = useUserStore();
const ratingList = ref<RatingInfo[]>();
const myRating = ref();
const getRatingList = async () => {
  const res = await getRatingListAPI();
  ratingList.value = res.result;
  console.log(ratingList.value);
  for (let i = 0; i < ratingList.value?.length; i++) {
    if (ratingList.value[i].username === userStore.profile?.username) {
      myRating.value = i + 1;
      break;
    }
  }
  let temp = ratingList.value[0];
  ratingList.value[0] = ratingList.value[1];
  ratingList.value[1] = temp;
}
const onStartBattle = ()=>{
  uni.switchTab({ url: '/pages/index/index' });
}
onLoad(() => {
  getRatingList();
})
</script>

<template>
  <view class="bg">
    <!-- 第一行，三个圆形头像 -->
    <view class="avatar-row">
      <view v-for="(player, index) in ratingList?.slice(0, 3)" :key="index" class="avatar-container"
        :class="{ 'avatar-large': index === 1 }">
        <image :src="checkFile(player.photo)" alt="Player Avatar" class="avatar" />
        <view class="text-container">
          <text class="name">{{ player.username }}</text>
          <text class="score">Score: {{ player.rating }}</text>
        </view>
      </view>
    </view>

    <!-- 第二行，其他选手信息列表 -->
    <view class="container">
      <scroll-view class="player-list" scroll-y >
        <view v-for="(otherPlayer, index) in ratingList?.slice(3, ratingList.length)" :key="index" class="player-item">
          <view class="left-container">
            <text class="player-rank">{{ index + 4 }}</text>
            <image :src="checkFile(otherPlayer.photo)" alt="Other Player Avatar" class="player-avatar" />
            <text class="player-name">{{ otherPlayer.username }}</text>
          </view>
          <text class="player-score">Score: {{ otherPlayer.rating }}</text>
        </view>
      </scroll-view>
    </view>

    <!-- 最底部，我的信息 -->
    <view class="my-info">
      <view class="left-container">
        <text class="my-rank">{{ myRating }}</text>
        <image :src="userStore.profile?.photo" alt="My Avatar" class="my-avatar" />
        <text class="my-name">{{ userStore.profile?.username }}</text>
      </view>
      <view class="right-container">
        <text class="my-score">{{ userStore.profile?.rating }}</text>
        <button class="start-battle" @click="onStartBattle">开始对战</button>
      </view>
    </view>
  </view>
</template>


<style lang="scss" scoped>
.bg {
  width: 100%;
  height: 100vh;
  background-color: rgb(84, 139, 225);
}

.avatar-row {
  padding-top: 50px;
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.avatar-container {
  text-align: center;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
}

.avatar-large .avatar {
  width: 80px;
  height: 80px;
}

.text-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.name,
.score {
  margin-top: 5px;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.player-list {
  height: 950rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: rgb(255, 255, 255);
  width: 95%;
  border-radius: 30rpx;
  padding-bottom: 10rpx;
}

.player-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  margin-bottom: 20px;
  width: 100%;
}

.left-container {
  display: flex;
  align-items: center;
}

.player-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-left: 10px;
}

.player-name,
.player-score,
.player-rank {
  margin-top: 5px;
  margin-left: 10px;
  padding-right: 15px;
}

.my-info {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  margin-top: 20px;
  padding: 10px;
  background-color: rgb(120, 168, 246);
  position: fixed;
  bottom: 0;
}

.my-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-left: 20rpx;
}

.my-name,
.my-score,
.my-rank {
  margin-top: 5px;
  margin-left: 10px;
}

.right-container {
  display: flex;
  align-items: center;
}

.start-battle {
  padding: 3px 12px;
  background-color: #0078d4;
  color: #ffffff;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
  margin-right: 20px;
}
</style>

