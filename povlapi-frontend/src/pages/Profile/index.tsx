import React, { useEffect, useRef, useState } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import {
  Avatar,
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
import { generateTextFileUsingGET, getUserByIdUsingGET, updateUserUsingPOST, uploadUsingPOST } from '@/services/povlapi-backend/userController';
import { ProColumns, ProFormInstance } from '@ant-design/pro-components';
import { UploadFile } from 'antd/lib/upload/interface';
import { UploadChangeParam } from 'antd/es/upload/interface';
import user from 'mock/user';

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
  if (Array.isArray(e)) return e
  return e && e.fileList
};

const FormDisabledDemo: React.FC<Props> = (props) => {
  const [oldData, setOldData] = useState<API.UserVO | null>(null);
  const [form] = Form.useForm();  

  const [avatarUrl, setAvatarUrl] = useState(''); // 用于存储头像URL的状态

  // 假设这是从后端API获取旧有数据的函数  
  const fetchOldData = async () => {  
    // 模拟异步获取数据  
    try {
      const response = await getUserByIdUsingGET();
      if (response && response.data) {
        console.log("res==>" + response.data)
        setOldData(response.data)
        const { userAvatar } = response.data
        if (userAvatar) {
          setAvatarUrl(userAvatar)
        }
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

  // 调用获取密钥
  const getAccessSecretKey = async () => {
    try {
      const response = await generateTextFileUsingGET();
      if (response) {
        message.success('密钥生成的file.txt文件已保存在E盘根目录')
      }
    } catch (error) {
      // 处理错误情况  
      console.error('Error get key:', error);
      // 根据需要设置state，例如设置为null或触发其他副作用  
      message.error('获取失败' + error);
    }
  }

  // 设置头像地址
  const handleChange = (info: UploadChangeParam<UploadFile<any>>) => {  
    if (info.file.status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === 'done') {
      // 假设服务器返回的响应体中包含一个 url 字段作为上传后的图片地址  
      const response = info.file.response as { data: string }; // 根据你的后端响应结构进行类型断言
      console.log("uploadRes===>" + response.data)
      if (response && response.data) {
        setAvatarUrl(response.data);  
        message.success('头像上传成功！');  
      } else {
        message.error('头像上传失败，请重试！');
      }  
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} 文件上传失败。`);  
    }  
  };  
  
  // 上传头像校验
  const handleBeforeUpload = (file: File) => {  
    const isJPG = file.type === 'image/jpeg';  
    if (!isJPG) {  
      message.error('只能上传 JPG 格式的图片!');  
      return false;  
    }  
    const isLt2M = file.size / 1024 / 1024 < 2;  
    if (!isLt2M) {  
      message.error('图片大小不能超过 2MB!');  
      return false;  
    }  
    return true;  
  };

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
        <Form.Item label="头像" valuePropName="fileList" getValueFromEvent={normFile}>
          <Upload 
            action="http://localhost:7529/api/user/upload"
            listType="picture-card"
            beforeUpload={handleBeforeUpload}
            onChange={handleChange}
          >
            {avatarUrl ? (  
              <Avatar src={avatarUrl} size={100} />  
            ) : (
              <button style={{ border: 0, background: 'none' }} type="button">
                <PlusOutlined />
                <div style={{ marginTop: 8 }}>点击上传头像</div>                
              </button>
            )}
          </Upload>
        </Form.Item>
        <Form.Item label="账户" name="userAccount">
          <Input disabled/>
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
        <Form.Item label="获取密钥">
          <Button type="primary" onClick={getAccessSecretKey}>
            获取
          </Button>
        </Form.Item>
      </Form>
  );
};

export default FormDisabledDemo;