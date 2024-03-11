import React, { useEffect, useRef, useState } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import {
  Button,
  Cascader,
  Checkbox,
  ColorPicker,
  DatePicker,
  Form,
  Input,
  InputNumber,
  Radio,
  Select,
  Slider,
  Switch,
  TreeSelect,
  Upload,
  message,
} from 'antd';
import { getUserByIdUsingGET, updateUserUsingPOST, uploadUsingPOST } from '@/services/povlapi-backend/userController';
import { ProColumns, ProFormInstance } from '@ant-design/pro-components';

export type Props = {
  values: API.User;
  columns: ProColumns<API.User>[];
  onCancel: () => void;
  onSubmit: (values: API.User) => Promise<void>;
  visible: boolean;
};

const { RangePicker } = DatePicker;
const { TextArea } = Input;

const handleSubmit = async (values: API.User) => {
  try {
    // 登录
    const res = await updateUserUsingPOST({
      ...values,
    });
    if (res.data) {
      const defaultLoginSuccessMessage = '修改成功！';
      message.success(defaultLoginSuccessMessage);
    }
  } catch (error) {
    const defaultLoginFailureMessage = '修改失败，请重试！';
    message.error('修改失败，' + error);
  }
};



const normFile = (e: any) => {
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};


const FormDisabledDemo: React.FC<Props> = (props) => {
  const [oldData, setOldData] = useState<API.UserVO | null>(null);
  const [form] = Form.useForm();  

  // 假设这是从后端API获取旧有数据的函数  
  const fetchOldData = async () => {  
    // 模拟异步获取数据  
    try {
      const response = await getUserByIdUsingGET();
      if (response && response.data) {
        console.log("res==>" + response.data)
        setOldData(response.data)
      }
    } catch (error) {
      // 处理错误情况  
      console.error('Error fetching data:', error);  
      // 根据需要设置state，例如设置为null或触发其他副作用  
      setOldData(null);  
    }
  };

  useEffect(() => {  
    fetchOldData();  
  }, []); 

  useEffect(() => {  
    if (oldData) {  
      form.setFieldsValue(oldData);  
      console.log(oldData)
    }  
  }, [oldData, form]); // 当oldData变化时，这个effect会运行

  function onFinishFailed(errorInfo: any): void {
    throw new Error('Function not implemented.');
  }

  return (
      <Form
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 14 }}
        layout="horizontal"
        style={{ maxWidth: 600 }}
        form={form}
        onFinish={async (values) => {
          handleSubmit?.(values as API.User);
        }}
        onFinishFailed={onFinishFailed}
      >
        <Form.Item label="形象" valuePropName="fileList" getValueFromEvent={normFile}>
          <Upload action="/upload.do" listType="picture-card">
            <button style={{ border: 0, background: 'none' }} type="button" onSubmit={async (values) => {
              
            }}>
              <PlusOutlined />
              <div style={{ marginTop: 8 }}>更改头像</div>
            </button>
          </Upload>
        </Form.Item>
        <Form.Item label="用户昵称" name="userName">
          <Input />
        </Form.Item>
        <Form.Item label="手机号" name="unionId">
          <Input />
        </Form.Item>
        <Form.Item label="性别" name="gender">
          <Radio.Group>
            <Radio value="男">男</Radio>
            <Radio value="女">女</Radio>
          </Radio.Group>
        </Form.Item>
        <Form.Item label="从事行业" name="job">
          <Select>
            <Select.Option value="暂无">暂无</Select.Option>
            <Select.Option value="互联网">互联网</Select.Option>
            <Select.Option value="金融会计">金融会计</Select.Option>
            <Select.Option value="网络安全">网络安全</Select.Option>
            <Select.Option value="驱动开发">驱动开发</Select.Option>
            <Select.Option value="人工智能">人工智能</Select.Option>
            <Select.Option value="自媒体">自媒体</Select.Option>
            <Select.Option value="机械自动化">机械自动化</Select.Option>
            <Select.Option value="商务管理">商务管理</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item label="用户简介" name="userProfile">
          <TextArea rows={4} />
        </Form.Item>
        <Form.Item label="提交更新">
          <Button type="primary" htmlType='submit'>
            更新
          </Button>
        </Form.Item>
      </Form>
  );
};

export default FormDisabledDemo;