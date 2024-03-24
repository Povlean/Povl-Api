// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 添加社区文章 POST /api/post/add */
export async function addPostUsingPOST(body: API.PostAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponselong>('/api/post/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除社区文章 POST /api/post/delete */
export async function deletePostUsingPOST(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/post/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 根据社区ID获取内容 GET /api/post/get/${param0} */
export async function getPostByIdUsingGET(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPostByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  const { postId: param0, ...queryParams } = params;
  return request<API.BaseResponsePostVO>(`/api/post/get/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取所有社区文章 GET /api/post/list */
export async function listPostUsingGET(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listPostUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListPostVO>('/api/post/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 分页查询文章 GET /api/post/list/page */
export async function listPostByPageUsingGET(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listPostByPageUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePagePostVO>('/api/post/list/page', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新文章内容 POST /api/post/update */
export async function updatePostUsingPOST(
  body: API.PostUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/post/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
