import React, { useState } from 'react';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Avatar, Card, Pagination, PaginationProps } from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import { Link } from '@umijs/max';

const { Meta } = Card;

const BasicFunc: React.FC = () => {

  return (
    <PageContainer title='集成接口测试'>
      <div style={{ display: 'flex', justifyContent: 'center' }}>
        <Card
          style={{ width: 300, margin: 20}}
          cover={
            <img
              alt="example"
              src="/images/yiyan.jpg"
            />
          }
          actions={[
            <Link to="/words">
              体验
            </Link> 
          ]}
        >
          <Meta
            title="随机生成鸡汤"
            description="用于随机生成一句鸡汤"
          />
        </Card>

        {/* <Card
          style={{ width: 300, margin: 20}}
          cover={
            <img
              alt="example"
              src="/images/temp1.jpg"
            />
          }
          actions={[
            <Link to="/music">
              体验
            </Link> 
          ]}
        >
          <Meta
            title="ERNIE-AI对话"
            description="体验与ERNIE对话"
          />
        </Card> */}

        <Card
          style={{ width: 300, margin: 20}}
          cover={
            <img
              alt="example"
              src="/images/liushui.jpg"
            />
          }
          actions={[
            <Link to="/stock">
              体验
            </Link>
          ]}
        >
          <Meta
            title="查询股票走势"
            description="使用券号查询股票走势"
          />
        </Card>
      </div>
      <Link to="/basicFunc2" style={{marginLeft: '50px' }}>
        上一页
      </Link> 
    </PageContainer>
  );

};

export default BasicFunc;