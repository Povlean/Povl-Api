package com.ean.project.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:TODO
 * @author:Povlean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostIdRequest implements Serializable {

    private Long id;

}
