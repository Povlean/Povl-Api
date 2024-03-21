// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** weatherCondition POST /api/basic/weather/${param0} */
export async function weatherConditionUsingPOST(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.weatherConditionUsingPOSTParams,
  options?: { [key: string]: any },
) {
  const { cityName: param0, ...queryParams } = params;
  return request<API.BaseResponseWeatherVO>(`/api/basic/weather/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}
