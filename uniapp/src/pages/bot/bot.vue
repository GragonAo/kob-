<script setup lang="ts">
import { delectBotAPI, getBotListAPI } from '@/services/bot';
import type { BotInfo } from '@/types/bot';
import { ref } from 'vue';
import { onShow } from '@dcloudio/uni-app';
import { computed } from 'vue';

const botList = ref<BotInfo[]>();


const getBotListData = async () => {
  const res = await getBotListAPI();
  selectedItems.value = [];
  botList.value = res.result;
  botList.value.forEach(bot => {
    selectedItems.value.push({ id: bot.id!, selected: false });
  });
}

const onDeleteBot = async (id: number, name: string) => {
  uni.showModal({
    content: '要删除"' + name + '"这个机器人吗？',
    success: async (res) => {
      if (res.confirm) {
        // 删除Bot
        await delectBotAPI(id);
        // 获取最新的Bot信息
        getBotListData();
      }
    },
  });
}
const selectedItems = ref<{
  id: number,
  selected: boolean
}[]>([]);
/** 修改选中状态 */
const onChangeSelected = (index: number) => {
  //取反选中状态
  selectedItems.value[index].selected = !selectedItems.value[index].selected;
}
/** 计算全选状态 */
const isSelectedAll = computed(() => {
  return selectedItems.value.length && selectedItems.value.every((v) => v.selected);
})
/** 计算选中的Bot数量 */
const selectedBotListCount = computed(() => {
  return selectedItems.value.reduce((sum, item) => sum + (item.selected ? 1 : 0), 0);
})
/** 修改全选状态 */
const onChangeSelectedAll = () => {
  //取反计算结果
  const _isSelectedAll = !isSelectedAll.value;
  //前端数据更新
  selectedItems.value.forEach((item) => {
    item.selected = _isSelectedAll
  })
};
/** 结算按钮 */
const onAllDeleteBot = async () => {
  if (selectedBotListCount.value === 0) {
    uni.showToast({
      icon: 'none',
      title: '请选择需要删除的Bot'
    })
    return;
  }
  // 构建 Promise 数组，每个 Promise 对应一个删除操作
  const deletePromises = selectedItems.value
    .filter(item => item.selected)
    .map(item => delectBotAPI(item.id));

  // 等待所有删除操作完成
  try {
    await Promise.all(deletePromises);
    // 刷新数据
    getBotListData();
  } catch (error) {
    console.error('Error deleting bots:', error);
    // 处理错误
  }
}

onShow(() => {
  getBotListData();
})
</script>

<template>
  <view class="viewport">
    <!-- Bot列表 -->
    <!-- 添加按钮 -->
    <view class="add-btn">
      <navigator hover-class="none" url="/pages/bot/botInfo">
        新建机器人
      </navigator>
    </view>
    <scroll-view class="scroll-view" scroll-y>

      <view v-if="botList?.length" class="bot">
        <uni-swipe-action class="bot-list">
          <!-- Bot项 -->
          <uni-swipe-action-item class="item" v-for="(item, index) in botList" :key="item.id">
            <view class="item-content">
              <!-- Toggle Button for Selection -->

              <view class="user">
                <text class="selected" @click="onChangeSelected(index)">
                  {{ selectedItems[index].selected ? "❌" : "✅" }}
                </text>
                {{ item.title }}
                <text v-if="item.isDefault === '1'" class="badge">默认</text>
              </view>
              <view class="locate">{{ item.description }}</view>
              <navigator class="edit" hover-class="none" :url="`/pages/bot/botInfo?id=${item.id!}`">
                修改
              </navigator>
            </view>
            <template #right>
              <button class="delete-button" @click="onDeleteBot(item.id!, item.title)">删除</button>
            </template>
          </uni-swipe-action-item>
        </uni-swipe-action>
      </view>
      <view v-else class="blank">暂无机器人</view>

      <!-- 吸底工具栏 -->
      <view class="toolbar">
        <text class="all" :class="isSelectedAll" @click="onChangeSelectedAll">全选</text>
        <view class="button-grounp">
          <view @click="onAllDeleteBot" class="button payment-button" :class="{ disabled: !selectedBotListCount }">
            删除({{ selectedBotListCount }}) </view>
        </view>
      </view>
    </scroll-view>

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
  align-items: center;
  min-height: 100%;
  background-color: #f4f4f4;
}

.scroll-view {
  flex: 1;
  padding-top: 100rpx;
  padding-bottom: 100rpx;
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
  position: fixed;
  height: 80rpx;
  width: 90%;
  text-align: center;
  line-height: 80rpx;
  margin: 20rpx 20rpx;
  color: #fff;
  border-radius: 80rpx;
  font-size: 30rpx;
  background-color: #27ba9b;
}

.selected {}

.checkbox {
  margin-right: 10rpx;
}

// 吸底工具栏
.toolbar {
  position: fixed;
  left: 0;
  right: 0;
  bottom: var(--window-bottom);
  z-index: 1;

  height: 100rpx;
  padding: 0 20rpx;
  display: flex;
  align-items: center;
  border-top: 1rpx solid #ededed;
  border-bottom: 1rpx solid #ededed;
  background-color: #fff;
  box-sizing: content-box;

  .all {
    margin-left: 25rpx;
    font-size: 14px;
    color: #444;
    display: flex;
    align-items: center;
  }

  .all::before {
    font-family: 'erabbit' !important;
    content: '\e6cd';
    font-size: 40rpx;
    margin-right: 8rpx;
  }

  .checked::before {
    content: '\e6cc';
    color: #27ba9b;
  }

  .text {
    margin-right: 8rpx;
    margin-left: 32rpx;
    color: #444;
    font-size: 14px;
  }

  .amount {
    font-size: 20px;
    color: #cf4444;

    .decimal {
      font-size: 12px;
    }

    &::before {
      content: '￥';
      font-size: 12px;
    }
  }

  .button-grounp {
    margin-left: auto;
    display: flex;
    justify-content: space-between;
    text-align: center;
    line-height: 72rpx;
    font-size: 13px;
    color: #fff;

    .button {
      width: 240rpx;
      margin: 0 10rpx;
      border-radius: 72rpx;
    }

    .payment-button {
      background-color: #ff0000;

      &.disabled {
        opacity: 0.6;
      }
    }
  }
}

// 底部占位空盒子
.toolbar-height {
  height: 100rpx;
}
</style>