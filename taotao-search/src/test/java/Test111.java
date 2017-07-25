

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;


public class Test111 {

	@Test
	public void test01() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		ItemMapper itemMapper =  (ItemMapper) ac.getBean("itemMapper");
		List<Item> itemList = itemMapper.getItemList();
		for (Item item : itemList) {
			System.out.println(item);
		}
	}
}
