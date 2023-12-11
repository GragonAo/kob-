<template>
  <view class="matching-block">
    <view class="player-block">
      <view class="player-info">
        <image class="head-portrait" src="/static/images/logo.jpg"/>
        <text class="player-username">玩家1</text>
      </view>
      <view class="player-info">
        <image class="head-portrait" src="/static/images/logo.jpg"/>
        <text class="player-username">玩家2</text>
      </view>
    </view>
    <button class="start-matching">开始匹配</button>
  </view>
</template>

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
  justify-content: space-between; /* 平分两个 .player-info 之间的空间 */
  width: 400rpx; /* 调整 .player-block 的宽度，根据实际需要调整 */
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
.start-matching{
  margin-top: 30rpx;
  margin-bottom: 10rpx;
}
</style>
