// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 书籍查询接口 GET /api/basic/book/${param0} */
export async function searchBookByIsbnUsingGET(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.searchBookByIsbnUsingGETParams,
  options?: { [key: string]: any },
) {
  const { isbnNum: param0, ...queryParams } = params;
  return request<API.BaseResponseSearchBookVO>(`/api/basic/book/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 随机头像生成 GET /api/basic/head */
export async function getRandomHeadUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponsestring>('/api/basic/head', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 热榜音乐接口 GET /api/basic/music */
export async function getMusicTop10UsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponseListMusicVO>('/api/basic/music', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 网易云音乐信息查询 POST /api/basic/music/${param0} */
export async function musicInfoUsingPOST(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.musicInfoUsingPOSTParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.BaseResponseMusicInfoVO>(`/api/basic/music/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 热榜新闻查询 POST /api/basic/news */
export async function hotNewListUsingPOST(options?: { [key: string]: any }) {
  return request<API.BaseResponseListNewsVO>('/api/basic/news', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 随机昵称生成 GET /api/basic/nickname */
export async function getRandomNicknameUsingGET(options?: { [key: string]: any }) {
  return request<API.BaseResponsestring>('/api/basic/nickname', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 股票趋势接口 GET /api/basic/stock/${param0} */
export async function stockUsingGET(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.stockUsingGETParams,
  options?: { [key: string]: any },
) {
  const { symbol: param0, ...queryParams } = params;
  return request<API.BaseResponseStockVO>(`/api/basic/stock/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 城市天气接口 GET /api/basic/weather/${param0} */
export async function weatherConditionUsingGET(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.weatherConditionUsingGETParams,
  options?: { [key: string]: any },
) {
  const { cityName: param0, ...queryParams } = params;
  return request<API.BaseResponseWeatherVO>(`/api/basic/weather/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 随机一言接口 GET /api/basic/words */
export async function searchBookByIsbnUsingGET1(options?: { [key: string]: any }) {
  return request<API.BaseResponsestring>('/api/basic/words', {
    method: 'GET',
    ...(options || {}),
  });
}
