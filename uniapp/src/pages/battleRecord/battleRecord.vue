<script setup lang="ts">
import { getBattleRecordAPI } from '@/services/battleRecord';
import { ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import { useUserStore } from '@/stores';
import { computed } from 'vue';
import type { BattleRecordInfo, PageParams } from '@/types/battleRecord';

const pageParams: Required<PageParams> = {
  page: 1,
  pageSize: 10
};

const userStore = useUserStore();
const recordList = ref<BattleRecordInfo[]>([]);
const finish = ref(false);

const enemyId = computed(() => {
  let id;
  recordList.value?.forEach(player => {
    if (player.userId1 !== userStore.profile?.id) {
      id = player.userId1;
    }
  });
  return id;
});

const result = (item: BattleRecordInfo) => {
  let res = "未知";
  if (item?.result === -1) {
    res = "平局";
  } else if (item?.result === userStore.profile?.id) {
    res = "胜利";
  } else {
    res = "失败";
  }
  return res;
};
const getBattleRecordData = async () => {

  if (finish.value === true) {
    return uni.showToast({
      title: '没有更多数据啦～',
      icon: 'none',
    });
  }
  const res = await getBattleRecordAPI(pageParams);
  if (res.code == 0) {
    recordList.value.push(...res.result.items);
    if (pageParams.page < res.result.pages) {
      pageParams.page++;
    } else {
      finish.value = true;
    }
  }

};

const resetData = () => {
  pageParams.page = 1;
  finish.value = false;
  recordList.value = [];
};

onLoad(() => {
  resetData();
  getBattleRecordData();
});
</script>

<template>
  <view class="viewport">
    <!-- bot列表 -->
    <scroll-view enable-back-to-top class="scroll-view" scroll-y style="min-height: 100vh;"
      @scrolltolower="getBattleRecordData">
      <view v-if="recordList?.length" class="bot">
        <view class="bot-list">
          <view class="item" v-for="item in recordList" :key="item.gameId">
            <view class="item-content">
              <view class="user">{{ userStore.profile?.id }}</view>
              <view class="vs-container">
                <view class="score">{{ item.avgRating }}</view>
                <view class="vs-icon">VS</view>
                <view class="time">{{ item.createtime }}</view>
              </view>
              <view class="user">{{ enemyId }}</view>
              <view class="additional-info">
                <view class="result">{{ result(item) }}</view>
              </view>
            </view>
          </view>
        </view>
        <view class="loading-text"> {{ finish ? '没有更多数据啦～' : '正在加载...' }} </view>
      </view>
      <view v-else class="blank">暂无对战记录</view>
      
    </scroll-view>
  </view>
</template>

<style lang="scss">
page {
  height: 100%;
  background-color: #f4f4f4;
}

.viewport {
  display: flex;
  flex-direction: column;
  min-height: 100%;
  height: 100%;
  background-color: #f4f4f4;
}

.scroll-view {
  flex: 1;
  overflow: hidden; //一定要加才能触发触底
}

.bot {
  padding: 0 20rpx;
  margin: 0 20rpx;
  border-radius: 10rpx;
  background-color: #fff;

  .item-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    /* 垂直居中对齐 */
    line-height: 1;
    padding: 40rpx 10rpx 38rpx;
    border-bottom: 1rpx solid #ddd;
    position: relative;
  }

  .user {
    font-size: 28rpx;
    color: #333;
  }

  .vs-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    /* 水平居中对齐 */
  }

  .vs-icon {
    font-size: 40rpx;
    color: #333;
    margin-top: 30rpx;
    margin-bottom: 30rpx;
  }

  .time {
    margin-bottom: 5rpx;
    font-size: 24rpx;
    color: #666;
  }

  .additional-info {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    /* 使内容靠右对齐 */
  }

  .result {
    margin-right: 20rpx;
  }

  .score {
    margin-top: 5rpx;
    font-size: 24rpx;
    color: #666;
  }
}

.blank {
  margin-top: 300rpx;
  text-align: center;
  font-size: 32rpx;
  color: #888;
}

// 加载提示文字
.loading-text {
  text-align: center;
  font-size: 28rpx;
  color: #666;
  padding: 20rpx 0;
}
</style>
