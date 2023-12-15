<template>
  <view class="content-block">
    <view class="bot-item">
      <view class="bot-select"></view>
      <view class="bot-info">
        <text>bot1</text>
      </view>
      <view class="bot-op">
        <button class="op-btn-delete">删除</button>
        <button class="op-btn-update">修改</button>
      </view>
    </view>
    <view class="bot-item">
      <view class="bot-info">
        <text>bot1</text>
      </view>
      <view class="bot-op">
        <button class="op-btn-delete">删除</button>
        <button class="op-btn-update">修改</button>
      </view>
    </view>
  </view>
</template>
<style lang='scss'>
page {
  width: 100%;
  height: 100%;
}

.op-btn-delete {
  background: red;
}

.op-btn-update {
  background: chartreuse;
}

.bot-op {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 30%;
  height: auto;
  padding: 2px 0px 2px 0px;
}

.bot-info {
  width: 100%;
  height: auto;
  /* 设置为 auto，让高度根据内容自适应 */
  margin: auto;
  /* 居中 */
  background: red;
}
.bot-select{
  width: 20px;
  height: 20px;
  margin: auto;
  margin-left: 5px;
  margin-right: 5px;
  background: white;
  border-radius: 50%;
}
.bot-item {
  height: 100px;
  width: 90%;
  background: wheat;
  border-radius: 10px;
  display: flex;
  flex-direction: row;
  margin-top: 20px;
}

.content-block {
  width: 100%;
  height: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #000;
  align-self: flex-start;
}</style>
