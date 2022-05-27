/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.yomahub.liteflow.test.script.qlexpress.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.slot.Slot;

@LiteflowComponent("d")
public class DCmp extends NodeComponent {

	@Override
	public void process() {
		DefaultContext context = this.getContextBean();
		context.setData("count",97);
		System.out.println("DCmp executed!");
	}

}
