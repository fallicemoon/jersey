package tools;

import java.lang.reflect.Field;

public class Tools {

	private static final String serialVersionUID = "serialVersionUID";

	
	/**
	 * 複製bean裡面的同名屬性(不會複製serialVersionUID)
	 * @param old
	 * @param target
	 */
	public static void copyBeanProperties(Object old, Object target) {
		Field[] oldFields = old.getClass().getDeclaredFields();
		
		for (Field field : oldFields) {
			if (serialVersionUID.equals(field.getName()))
				continue;
			field.setAccessible(true);
			try {
				Object value = field.get(old);
				field.set(target, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
		}

	}
	
	
	
	
	
	
	
}
