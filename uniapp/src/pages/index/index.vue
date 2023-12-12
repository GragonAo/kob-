<template>
    <!-- <view class="matching">
    <Matching/>
    </view> -->
    <!-- <PlayGround/> -->
    <view>
    <button @click="connectWebSocket">连接WebSocket</button>
  </view>
</template>

<script setup lang='ts'>
import Matching from './components/matching/matching.vue';
import PlayGround from './components/PlayGround/PlayGround.vue';

import { useUserStore } from '@/stores';

const userStore = useUserStore();
const connectWebSocket = ()=>{
      const wsUrl = 'ws://127.0.0.1:3300/websocket/'+userStore.profile?.token;
      console.log(wsUrl);
      // 创建WebSocket连接
      const socketTask = uni.connectSocket({
        url: wsUrl,
        success() {
          console.log('WebSocket连接成功');
        },
        fail() {
          console.log('WebSocket连接失败');
        }
      });

      // 监听WebSocket连接打开事件
      uni.onSocketOpen(function (res) {
        console.log('WebSocket连接已打开！');
        // 连接成功后发送一条测试消息
        uni.sendSocketMessage({
          data: 'Hello, WebSocket!'
        });
      });

      // 监听WebSocket接收到服务器的消息事件
      uni.onSocketMessage(function (res) {
        console.log('收到服务器内容：' + res.data);
      });

      // 监听WebSocket错误
      uni.onSocketError(function (res) {
        console.log('WebSocket连接打开失败，请检查！');
      });

      // 监听WebSocket关闭
      uni.onSocketClose(function (res) {
        console.log('WebSocket 已关闭！');
      });
}

</script>

<style lang='scss'>
page{
  width: 100%;
  height: 100%;
  background: white;
}
.matching{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>