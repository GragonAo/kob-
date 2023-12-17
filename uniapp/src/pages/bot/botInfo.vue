<template>
  <view class="content">
    <uni-forms ref="formRef" :rules="rules" :model="form">
      <!-- 表单内容 -->
      <uni-forms-item name="title" class="form-item">
        <text class="label">标题</text>
        <input v-model="form.title" class="input" placeholder="请填写标题信息" />
      </uni-forms-item>
      <uni-forms-item name="description" class="form-item">
        <text class="label">描述信息</text>
        <input v-model="form.description" class="input" placeholder="请填写描述信息" />
      </uni-forms-item>
      <uni-forms-item name="content" class="form-item">
        <text class="label">代码内容</text>
        <textarea v-model="form.content" maxlength="500" class="textarea" placeholder="请填写你的代码"></textarea>
      </uni-forms-item>
      <view class="form-item">
        <label class="label">设为默认机器人</label>
        <switch class="switch" :checked="form.isDefault === '1'" color="#27ba9b" @change="onSwitchChange" />
      </view>
    </uni-forms>
  </view>
  <!-- 提交按钮 -->
  <button class="button" @click="onSubmit">保存并使用</button>
</template>
  
<script setup lang="ts">
import { addBotAPI, getBotAPI, updateBotAPI } from '@/services/bot';
import type { BotInfo } from '@/types/bot';
import type { UniFormsInstance } from '@uni-helper/uni-ui-types';
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app';
const query = defineProps<{
  id?: string
}>();
// 表单数据
const form = ref<BotInfo>({
  title: '', // 标题
  description: '', // 描述
  content: '', // 代码内容
  isDefault: '0', // 默认Bot，1为是，0为否
})

const onSwitchChange: UniHelper.UniListItemOnSwitchChange = (e) => {
  form.value.isDefault = (e.detail.value ? 1 : 0).toString();
}

/** 表单校验规则 */
const rules: UniHelper.UniFormsRules = {
  titel:
  {
    rules: [{ required: true, errorMessage: '请填写标题' }],
  },
  description:
  {
    rules: [{ required: true, errorMessage: '请填写描述' }],
  },
  content:
  {
    rules: [{ required: true, errorMessage: '请填写代码' }],
  },
}
const formRef = ref<UniFormsInstance>();
const onSubmit = async () => {
  try {
    await formRef.value.validate();

    if (query.id) {
      await updateBotAPI(Number.parseInt(query.id), form.value);
    }
    else {
      await addBotAPI(form.value);
    }
    uni.showToast({
      icon: 'success',
      title: '提交成功',
      success(res) {
        //返回上一级
        uni.navigateBack();
      }
    });
  } catch (error) {
    uni.showToast({
      icon: 'error',
      title: '请按要求填写',
    });
  }
}
/** 根据ID获取地址信息 */
const getBotInfoData = async () => {
  if (query.id) {
    const res = await getBotAPI(Number.parseInt(query.id));
    console.log(res);
    Object.assign(form.value, res.result);
  }
}
onLoad(() => {
  getBotInfoData();
})
</script>
  
<style lang="scss">
page {
  background-color: #f4f4f4;
}

.content {
  margin: 20rpx 20rpx 0;
  padding: 0 20rpx;
  border-radius: 10rpx;
  background-color: #fff;

  .form-item,
  .uni-forms-item {
    display: flex;
    align-items: center;
    min-height: 96rpx;
    padding: 25rpx 10rpx 40rpx;
    background-color: #fff;
    font-size: 28rpx;
    border-bottom: 1rpx solid #ddd;
    position: relative;
    margin-bottom: 0;

    // 调整 uni-forms 样式
    .uni-forms-item__content {
      display: flex;
    }

    .uni-forms-item__error {
      margin-left: 200rpx;
    }

    &:last-child {
      border: none;
    }

    .label {
      width: 200rpx;
      color: #333;
    }

    .input {
      flex: 1;
      display: block;
      height: 46rpx;
    }

    .switch {
      position: absolute;
      right: -20rpx;
      transform: scale(0.8);
    }

    .picker {
      flex: 1;
    }

    .placeholder {
      color: #808080;
    }
  }
}

.button {
  height: 80rpx;
  margin: 30rpx 20rpx;
  color: #fff;
  border-radius: 80rpx;
  font-size: 30rpx;
  background-color: #27ba9b;
}
</style>
  