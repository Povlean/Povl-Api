import { LikeOutlined, MessageOutlined, StarOutlined } from '@ant-design/icons';
import React, { useEffect, useState } from 'react';
import { Avatar, FloatButton, List, Space } from 'antd';
import { listPostByPageUsingGET, listPostUsingGET } from '@/services/povlapi-backend/postController';
import FloatButton_ from './FloatButton_';
import { PageContainer } from '@ant-design/pro-components';
import Link from 'antd/es/typography/Link';

  const Community: React.FC = () => {

    const [resData, setResData] = useState<API.PostVO[]>([]);
    const [loading, setLoading] = useState(false);

    const listConmunity = async() => {
      setLoading(true);
      const res = await listPostUsingGET({});
      try {
        if (res.data) {
          console.log(res.data);
          setResData(res.data);
        } else {
          setResData([]);
        }
      } catch (e: any) {
        console.log(e)
      }
      setLoading(false);
    }

    useEffect(() => {
      listConmunity();
    }, []);

    const data = resData.map((data, i) => ({
      href: 'https://ant.design',
      title: `${data.userName}用户`,
      avatar: data.userAvatar,
      description: data.content,
      content: data.title,
      image: data.image,
      thumb: data.thumbNum,
      favour: data.favourNum
    }));
  
  
  
    const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
      <Space>
        {React.createElement(icon)}
        {text}
      </Space>
    );


    return(  
    <PageContainer>
      <List
      itemLayout="vertical"
      size="large"
      pagination={{
        onChange: (page) => {
          console.log(page);
        },
        pageSize: 6,
      }}
      dataSource={data}

      renderItem={(item) => (
        <List.Item
          key={item.title}
          actions={[
            <IconText icon={StarOutlined} text={JSON.stringify(item.favour)} key="list-vertical-star-o" />,
            <IconText icon={LikeOutlined} text={JSON.stringify(item.thumb)} key="list-vertical-like-o" />,
            <IconText icon={MessageOutlined} text="2" key="list-vertical-message" />,
          ]}
          // extra={
          //   <img
          //     width={272}
          //     alt="logo"
          //     src={item.image}
          //   />
          // }
        >
          <List.Item.Meta
            avatar={<Avatar src={item.avatar} />}
            title={<a href={item.href}>{item.title}</a>}
            description={item.description}
          />
          {item.content}
        </List.Item>
      )}/>
      <Link href='/article'>
        <FloatButton />
      </Link>
    </PageContainer>
    );
  }  
export default Community;