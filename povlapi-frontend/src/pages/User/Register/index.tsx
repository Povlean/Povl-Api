import React from 'react';
import { Button, Checkbox, Form, type FormProps, Input, message } from 'antd';
import { userRegisterUsingPOST } from '@/services/povlapi-backend/userController'

type FieldType = {
  userAccount?: string;
  userPassword?: string;
  checkPassword?: string;
  userName?: string;
};

const onFinish: FormProps<FieldType>["onFinish"] = (values) => {
  console.log('Success:', values);
};

const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
  console.log('Failed:', errorInfo);
};

const handleSubmit = async (values: API.UserRegisterRequest) => {
  try {
    // 登录
    const res = await userRegisterUsingPOST({
      ...values,
    });
    if (res.data) {
      const defaultLoginSuccessMessage = '注册成功！';
      message.success(defaultLoginSuccessMessage);
    }
  } catch (error) {
    const defaultLoginFailureMessage = '注册失败，请重试！';
    message.error('注册失败，' + error);
  }
};

const App: React.FC = () => (
  <div style={{backgroundImage: "url('/images/background.jpeg')", backgroundSize: '100% 100%'}}>
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
    <Form
    title="用户注册页面"
    name="basic"
    labelCol={{ span: 8 }}
    wrapperCol={{ span: 16 }}
    style={{ maxWidth: 600 }}
    onFinish={async (values) => {
      await handleSubmit(values as API.UserRegisterRequest);
    }}
    onFinishFailed={onFinishFailed}
  >
    <Form.Item<FieldType>
      label="用户账户"
      name="userAccount"
      rules={[{ required: true, message: '请输入你的账户!' }]}
    >
      <Input />
    </Form.Item>

    <Form.Item<FieldType>
      label="用户昵称"
      name="userName"
      rules={[{ required: true, message: '请输入你的昵称!' }]}
    >
      <Input />
    </Form.Item>

    <Form.Item<FieldType>
      label="密码"
      name="userPassword"
      rules={[{ required: true, message: '请输入注册密码!' }]}
    >
      <Input.Password />
    </Form.Item>

    <Form.Item<FieldType>
      label="确认密码"
      name="checkPassword"
      rules={[{ required: true, message: '请确认注册密码!' }]}
    >
      <Input.Password />
    </Form.Item>

    <Form.Item<FieldType>
      valuePropName="checked"
      wrapperCol={{ offset: 8, span: 16 }}
    >
    </Form.Item>

    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
      <Button type="primary" htmlType="submit" style={{width:'40%'}}>
        注册
      </Button>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <Button type="primary" style={{width:'40%'}}>
        返回
      </Button>
    </Form.Item>
  </Form>
  </div>
  </div>
  
);

export default App;