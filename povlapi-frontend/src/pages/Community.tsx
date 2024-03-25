import { HeartOutlined, LikeOutlined, MessageOutlined, StarOutlined } from '@ant-design/icons';
import React, { useEffect, useState } from 'react';
import { Avatar, FloatButton, List, Space, message } from 'antd';
import { listPostByPageUsingGET, listPostUsingGET, thumbPostUsingGET } from '@/services/povlapi-backend/postController';
import FloatButton_ from './FloatButton_';
import { PageContainer } from '@ant-design/pro-components';
import Link from 'antd/es/typography/Link';
import { getPostByIdUsingGET } from '@/services/povlapi-backend/postController';

  // 定义 item 的类型  
  interface ItemType {  
    id: number; // 假设 id 是字符串类型  
    // ... 可以添加更多 item 的属性及其类型  
  }  
  
// 定义组件的 props 类型  
  interface MyComponentProps {  
    item?: ItemType; // 使用 ItemType 作为 item 的类型，并使用 ? 表示它是可选的  
  }  

  const Community: React.FC<MyComponentProps> = ({ item }) => {

    const [resData, setResData] = useState<API.PostVO[]>([]);
    const [loading, setLoading] = useState(false);
    const [loading1, setLoading1] = useState(false);
    const [error, setError] = useState('');  
    const [thumb, setThumb] = useState(0);


    interface thumbPostUsingGETParams {  
      id: number;  
    }

    const thumbUpdate = async(postId: number | undefined, thumbNum: number | undefined) => {
      console.log("thumbUpdate===>" + postId);
      if (postId !== undefined && thumbNum !== undefined) {
        const params: thumbPostUsingGETParams = { id: postId };
        const res = await thumbPostUsingGET(params);
        setThumb(thumbNum);
        if (res.message === 'ok') {
          console.log(res)
          if (res.data === 'add') {
            message.success("点赞成功")
          } else {
            message.success("取消点赞")
          }
          await listConmunity(); // 如果需要等待listConmunity完成，则使用await
        } else {
          message.error("操作失败")
        }
      } 
    };

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

    const data = resData.map((data) => ({
      id: data.postId,
      href: 'https://ant.design',
      title: `${data.userName}用户`,
      avatar: data.userAvatar,
      description: data.content,
      content: data.title,
      image: data.image,
      thumb: data.thumbNum,
      comment: data.commentNum
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
            <div onClick={() => thumbUpdate(item.id, item.thumb)}>
            {/* <div> */}
              <IconText icon={LikeOutlined} text={JSON.stringify(item.thumb)} key="list-vertical-star-o" />
            </div>,
            <div>
              <IconText icon={MessageOutlined} text={JSON.stringify(item.comment)} key="list-vertical-message" />
            </div>
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