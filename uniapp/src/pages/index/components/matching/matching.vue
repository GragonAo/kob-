<template>
  <view class="matching-block">
    <view class="player-block">
      <view class="player-info" v-if="!gameStore.userList">
        <image v-if="userStore.profile" class="head-portrait" :src="checkFile(userStore.profile?.photo)" />
        <image v-else class="head-portrait" src="/static/images/defPhoto.png" />
        <text class="player-username">{{ userStore.profile?.username }}</text>
      </view>
      <view class="player-info" v-for="item in gameStore.userList" :key="item.id">
        <image class="head-portrait" :src="checkFile(item.photo)" />
          <text class="player-username">{{ item.username }}</text>
      </view>
    </view>
    <button @click="startMatching" v-if="gameStore.gameState === GameState.Null && !gameStore.userList" class="start-matching">开始匹配</button>
    <button @click="unMatching" v-if="gameStore.gameState === GameState.Matching && !gameStore.userList" class="start-matching">取消匹配</button>
  </view>
</template>

<script setup lang="ts">
import { GameState } from '@/enums/game';
import { addPlayerToMatchingAPI, removePlayerToMatchingAPI } from '@/services/matching';
import { useUserStore } from '@/stores';
import { useGameStore } from '@/stores/modules/game';
import { checkFile } from '@/utils/checkFile';
const userStore = useUserStore();
const gameStore = useGameStore();
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

<style lang='scss'>
.matching-block {
  width: 690rpx;
  align-content: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #B5EF61;
  border-radius: 10px;
  /* 添加阴影 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.player-block {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  /* 平分两个 .player-info 之间的空间 */
  width: 400rpx;
  /* 调整 .player-block 的宽度，根据实际需要调整 */
  margin-top: 10px;

}

.player-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.head-portrait {
  width: 100rpx;
  height: 100rpx;
  border-radius: 50%;
}

.player-username {
  margin-top: 10rpx;
}

.start-matching {
  margin-top: 30rpx;
  margin-bottom: 10rpx;
}
</style>