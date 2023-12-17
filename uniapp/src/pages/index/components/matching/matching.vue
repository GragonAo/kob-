<template>
  <view class="Season-bg">
    <text class="Season-txt">S1赛季</text>
  </view>
  <view class="content">
    <image class="rating-bg" 
    :src= "imgValue"  />
    <text class="rating-txt">{{ txtValue }}</text>
  </view>
  <button class="Start-Btn" v-if="gameStore.gameState === GameState.Null && !gameStore.userList" @click="startMatching">开始对战吧！</button>
  <button class="Start-Btn" v-if="gameStore.gameState === GameState.Matching && !gameStore.userList" @click="unMatching">取消对战！</button>
</template>

<script setup lang="ts">
import { GameState } from '@/enums/game';
import { addPlayerToMatchingAPI, removePlayerToMatchingAPI } from '@/services/matching';
import { useUserStore } from '@/stores';
import { useGameStore } from '@/stores/modules/game';
import { checkFile } from '@/utils/checkFile';
import { computed } from 'vue';
const userStore = useUserStore();
const gameStore = useGameStore();
const imgValue = computed(()=>{
    let res = checkFile("/uploads/logo.jpg");
    if(gameStore.userList?.length){
        gameStore.userList?.forEach(player => {
            if(player.id !== userStore.profile?.id){
              res = checkFile(player.photo);
            }
        });
    }
    return res;
});
const txtValue = computed(()=>{
  let txt = userStore.profile?.rating?.toString();
    if(gameStore.userList?.length){
        gameStore.userList?.forEach(player => {
            if(player.id !== userStore.profile?.id){
              txt = player.username;
            }
        });
    }
    return txt;
})
/** 开始匹配 */
const startMatching = async () => {
  const res = await addPlayerToMatchingAPI();
  if (res.code == 0) {
    gameStore.setGameState(GameState.Matching);
  } else {
    uni.showToast({
      icon: 'none',
      title: '开始匹配失败，请检查网络状况！！！'
    })
  }
}
/** 取消匹配 */
const unMatching = async () => {
  const res = await removePlayerToMatchingAPI();
  if (res.code == 0) {
    gameStore.setGameState(GameState.Null);
  } else {
    uni.showToast({
      icon: 'none',
      title: '停止匹配失败，请检查网络状况！！！'
    })
  }
}
</script>

<style lang='scss' scoped>
.Season-bg {
  width: 100%;
  height: 10%;
  background-color: rgb(38, 81, 165);
  display: flex;
  align-items: center;
  .Season-txt {
    color: white;
    font-size: 60rpx;
    margin-left: 20rpx;
    text-align: left;
  }
}

.content {
  width: 100%;
  height: 70%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .rating-bg {
    margin-top: 20rpx;
    width: 400rpx;
    height: 400rpx;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
  }

  .rating-txt {
    text-align: center;
    font-size: 40rpx;
    margin-top: 20rpx;
  }

  .Start-Btn {
    margin-top: 50rpx;
    background: #f8e172; /* 使用十六进制颜色值 */
    color: #ca7731; /* 使用十六进制颜色值 */
    border-radius: 10rpx;
    text-align: center;
    width: 90%;
  }
}

// Media queries for responsiveness
@media screen and (max-width: 600px) {
  .Season-txt {
    font-size: 40rpx;
    margin-left: 10rpx;
  }

  .rating-bg {
    width: 300rpx;
    height: 300rpx;
  }

  .rating-txt {
    font-size: 30rpx;
    margin-top: 10rpx;
  }

  .Start-Btn {
    margin-top: 20rpx;
    width: 80%;
  }
}
</style>