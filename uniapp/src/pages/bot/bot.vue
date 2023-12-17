<script setup lang="ts">
import { delectBotAPI, getBotListAPI } from '@/services/bot';
import type { BotInfo } from '@/types/bot';
import { ref } from 'vue';
import { onShow } from '@dcloudio/uni-app';

const botList = ref<BotInfo[]>();
const getBotListData = async () => {
  const res = await getBotListAPI();
  console.log(res);
  botList.value = res.result;
}
/** 删除收获地址 */
const onDeleteBot = (id:number,name:string)=>{
  uni.showModal({
    content:'要删除"'+name+'"这个机器人吗？',
    success: async (res)=>{
      if(res.confirm){
        //删除地址
        await delectBotAPI(id);
        //获取最新的地址信息
        getBotListData();
      }
    },
  })
}

onShow(() => {
  getBotListData();
}) 
</script>
<template>
  <view class="viewport">
    <!-- Bot列表 -->
    <scroll-view class="scroll-view" scroll-y>
      <view v-if="botList?.length" class="bot">
        <uni-swipe-action class="bot-list">
          <!-- Bot项 -->
          <uni-swipe-action-item class="item"  v-for="item in botList" :key="item.id">
            <view class="item-content">
              <view class="user">
                {{ item.title }}
                <text v-if="item.isDefault === '1'" class="badge">默认</text>
              </view>
              <view class="locate">{{ item.description }}</view>
              <navigator class="edit" hover-class="none" :url="`/pages/bot/botInfo?id=${item.id!}`">
                修改
              </navigator>
            </view>
            <template #right>
              <button class="delete-button" @click="onDeleteBot(item.id!,item.title)">删除</button>
            </template>
          </uni-swipe-action-item>
        </uni-swipe-action>
      </view>
      <view v-else class="blank">暂无机器人</view>
    </scroll-view>
    <!-- 添加按钮 -->
    <view class="add-btn">
      <navigator hover-class="none" url="/pages/bot/botInfo">
        新建机器人
      </navigator>
    </view>
  </view>
</template>

<style lang="scss">
.page {
  height: 100%;
  overflow: hidden;
}

.delete-button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 100%;
  font-size: 28rpx;
  color: #fff;
  border-radius: 0;
  padding: 0;
  background-color: #cf4444;
}

.viewport {
  display: flex;
  flex-direction: column;
  min-height: 100%;
  background-color: #f4f4f4;
}

.scroll-view {
  flex: 1;
  padding-top: 20rpx;
}
.bot {
  padding: 0 20rpx;
  margin: 0 20rpx;
  border-radius: 10rpx;
  background-color: #fff;

  .item-content {
    line-height: 1;
    padding: 40rpx 10rpx 38rpx;
    border-bottom: 1rpx solid #ddd;
    position: relative;

    .edit {
      position: absolute;
      top: 36rpx;
      right: 30rpx;
      padding: 2rpx 0 2rpx 20rpx;
      border-left: 1rpx solid #666;
      font-size: 26rpx;
      color: #666;
      line-height: 1;
    }
  }

  .item:last-child .item-content {
    border: none;
  }

  .user {
    font-size: 28rpx;
    margin-bottom: 20rpx;
    color: #333;

    .contact {
      color: #666;
    }

    .badge {
      display: inline-block;
      padding: 4rpx 10rpx 2rpx 14rpx;
      margin: 2rpx 0 0 10rpx;
      font-size: 26rpx;
      color: #27ba9b;
      border-radius: 6rpx;
      border: 1rpx solid #27ba9b;
    }
  }

  .locate {
    line-height: 1.6;
    font-size: 26rpx;
    color: #333;
  }
}

.blank {
  margin-top: 300rpx;
  text-align: center;
  font-size: 32rpx;
  color: #888;
}

.add-btn {
  height: 80rpx;
  text-align: center;
  line-height: 80rpx;
  margin: 30rpx 20rpx;
  color: #fff;
  border-radius: 80rpx;
  font-size: 30rpx;
  background-color: #27ba9b;
}
</style>