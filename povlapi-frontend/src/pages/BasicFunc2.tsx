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
              src="/images/catAvatar.jpg"
            />
          }
          actions={[
            <Link to="/avatar">
              体验
            </Link> 
          ]}
        >
          <Meta
            title="随机生成头像接口"
            description="用于随机生成一张用于头像的图片"
          />
        </Card>

        <Card
          style={{ width: 300, margin: 20}}
          cover={
            <img
              alt="example"
              src="/images/music01.png"
            />
          }
          actions={[
            <Link to="/musicSearch">
              体验
            </Link> 
          ]}
        >
          <Meta
            title="网易云音乐信息查询"
            description="使用音乐id返回具体的音乐信息"
          />
        </Card>

        <Card
          style={{ width: 300, margin: 20}}
          cover={
            <img
              alt="example"
              src="/images/news.jpg"
            />
          }
          actions={[
            <Link to="/news">
              体验
            </Link> 
          ]}
        >
          <Meta
            title="获取头条新闻"
            description="用于查询近期热点新闻"
          />
        </Card>
      </div>
      <div style={{ display: 'inline-block'}}>
        <Link to="/basic" style={{marginLeft: '50px' }}>
          上一页
        </Link> 
        <Link to="/basicFunc3" style={{marginLeft: '900px' }}>
          下一页
        </Link> 
      </div>
    </PageContainer>
  );

};

export default BasicFunc;