import { EditTwoTone, HeartOutlined, LikeOutlined, MessageOutlined, MessageTwoTone, SettingOutlined, StarOutlined } from '@ant-design/icons';
import React, { useEffect, useState } from 'react';
import { Avatar, Button, FloatButton, Form, Input, List, Modal, Popover, Select, Space, message } from 'antd';
import { addCommentUsingPOST, listPostByPageUsingGET, listPostUsingGET, thumbPostUsingGET } from '@/services/povlapi-backend/postController';
import { PageContainer } from '@ant-design/pro-components';
import Link from 'antd/es/typography/Link';
import Collapse, { CollapseProps, ExpandIconPosition } from 'antd/es/collapse/Collapse';

  // 定义 item 的类型  
  interface ItemType {  
    id: number; // 假设 id 是字符串类型  
    // ... 可以添加更多 item 的属性及其类型  
  }  
  
// 定义组件的 props 类型  
  interface MyComponentProps {  
    item?: ItemType; // 使用 ItemType 作为 item 的类型，并使用 ? 表示它是可选的  
  }  

  const Community: React.FC<MyComponentProps> = () => {

    const [selectedItem, setSelectedItem] = useState(null); // 用于存储当前选中的列表项  
    const [isModalVisible, setIsModalVisible] = useState(false); // 用于控制模态框的显示与隐藏 
    const [resData, setResData] = useState<API.PostVO[]>([]);
    const [loading, setLoading] = useState(false);
    const [thumb, setThumb] = useState(0);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [comment, setComment] = useState(''); // 初始化状态来保存评论内容

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
      title: `${data.userName}`,
      avatar: data.userAvatar,
      description: data.content,
      content: data.title,
      image: data.image,
      thumbNum: data.thumbNum,
      commentNum: data.commentNum,
      commentVOList: data.commentVOList
    }));
  
    const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
      <Space>
        {React.createElement(icon)}
        {text}
      </Space>
    );

    const generateCommentContent = (commentVOList: any[] | undefined) => {  
      return (  
        <div>  
          {commentVOList.map((comment, index) => (
            <p key={index}>  
              <Avatar src={comment.userAvatar}></Avatar><strong>{comment.userName} : </strong> {comment.comment}  
            </p>  
          ))}  
        </div>  
      );  
    };
  
    const handleModalOk = () => {
      console.log('用户提交了评论', selectedItem);
      if (selectedItem !== undefined) {  
        const body: API.AddCommentRequest = {  
          id: selectedItem.id,  
          comment: comment,  
          // 设置其他需要的字段...  
        };  
        addCommentUsingPOST(body)  
          .then(() => {  
            setIsModalVisible(false);  
            // 处理成功逻辑  
            message.success("评论成功")
            listConmunity();
          })  
          .catch((error) => {  
            // 处理错误逻辑  
            message.success("评论失败")
          });  
      } else {  
        // 处理 postId 为 undefined 的情况  
      }  
      setIsModalVisible(false);
    };
  
    const handleModalCancel = () => {  
      // 清除选中的项  
      setSelectedItem(null);  
      // 关闭模态框  
      setIsModalVisible(false);  
    };  

    // 当点击列表项时，设置选中的项并显示模态框  
    const handleItemClick = (item: React.SetStateAction<null>) => {  
      setSelectedItem(item);    
      setIsModalVisible(true);  
    };

    const handleInputChange = (e: any) => {  
      setComment(e.target.value); // 更新评论状态  
    };  

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
            <div onClick={() => thumbUpdate(item.id, item.thumbNum)}>
            {/* <div> */}
              <IconText icon={LikeOutlined} text={JSON.stringify(item.thumbNum)} key="list-vertical-star-o" />
            </div>,
            <div>
              <IconText icon={MessageOutlined} text={JSON.stringify(item.commentNum)} key="list-vertical-message" />
            </div>,
            <div>
              <Space wrap>
                <Popover content={generateCommentContent(item.commentVOList)} title="评论" trigger="click">
                  <Button>                  
                    查看评论 <MessageTwoTone /> 
                  </Button>
                </Popover>
                <>
                  <Button onClick={() => handleItemClick(item)}>
                    发表评论 <EditTwoTone />
                  </Button>
                  <Modal title="发表评论" visible={isModalVisible} onOk={handleModalOk} onCancel={handleModalCancel}>
                    <Input value={comment} onChange={handleInputChange}/>
                  </Modal>
                </>
              </Space>
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
            description={<strong>{item.description}</strong>}
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