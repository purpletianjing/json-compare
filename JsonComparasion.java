public class JsonComparasion {
	public static void jsonCompare() {
		try {
			JSONParser parser = new JSONParser();
			Object object = parser.parse(new FileReader("C:\\\\Users\\\\s\\\\Desktop\\\\json-example"));
			Object object1 = parser.parse(new FileReader("C:\\\\Users\\\\s\\\\Desktop\\\\json-example2"));
			Gson gson = new Gson();	
			String str1 = gson.toJson(object, Object.class);
			String str2 = gson.toJson(object1, Object.class);
			long startTime=System.currentTimeMillis();
			JSONObject jsonObject = new JSONObject(str1);
			JSONObject jsonObject1 = new JSONObject(str2);
			getDifferenceAmongTwoJson(jsonObject, jsonObject1);
			long endTime=System.currentTimeMillis();
			System.out.println("程序运行时间： "+ (endTime-startTime) +"ms");
			System.out.println("hahahah");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static JSONObject getDifferenceAmongTwoJson(JSONObject jsonObjectSource,
			JSONObject jsonObjectComparasion) {		
		Iterator<String> iterator =jsonObjectSource.keys();
		while(iterator.hasNext()) {
			String key = iterator.next();
			if (!(jsonObjectSource.get(key) instanceof JSONObject) && !(jsonObjectSource.get(key) instanceof JSONArray)) {
				if (jsonObjectSource.get(key).equals(jsonObjectComparasion.get(key))) {
					jsonObjectComparasion.put(key, "null");
				}
			} else if (jsonObjectSource.get(key) instanceof JSONObject) {
				JSONObject jsonObjectSourceChild = (JSONObject) jsonObjectSource.get(key);
				JSONObject jsonObjectComparasionChild = (JSONObject) jsonObjectComparasion.get(key);
				JSONObject result = getDifferenceAmongTwoJson(jsonObjectSourceChild, 
						jsonObjectComparasionChild);
				jsonObjectComparasion.put(key, result);
			} else {
				JSONArray jsonArray = getDifferenceBetweenTwoJsonArrays(key, new JSONArray(jsonObjectSource.get(key).toString()), 
						new JSONArray(jsonObjectComparasion.get(key).toString()));
				jsonObjectComparasion.put(key, jsonArray);
			}
		}
		return jsonObjectComparasion;
	}	

	private static JSONArray getDifferenceBetweenTwoJsonArrays(String key, JSONArray jsonArraySource, JSONArray jsonArrayComparasion) {
		
		Iterator<Object> objectsSource = jsonArraySource.iterator();
		Iterator<Object> objectsComparasion = jsonArrayComparasion.iterator();
		for(int i = 0; objectsSource.hasNext(); i++) {
			JSONObject jsonObjectSource = new JSONObject(objectsSource.next().toString());
			JSONObject jsonObjectComparasion = new JSONObject(objectsComparasion.next().toString());
			JSONObject jo = getDifferenceAmongTwoJson(jsonObjectSource, jsonObjectComparasion);
			jsonArrayComparasion.put(i, jo);
		}
		return jsonArrayComparasion;
	}
}