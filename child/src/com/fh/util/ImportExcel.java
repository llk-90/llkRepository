package com.fh.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by 860115007 on 2016/8/3. <br />
 * version:0.1
 */
public class ImportExcel {

	private final RectIdx rectIdx;
	private final InputStream in;
	private final DataAdapter adapter;
	private HSSFWorkbook wb;
	private ArrayList<HashMap<String, Object>> result = new ArrayList<>();
	private String errMsgs = "";

	public ImportExcel(InputStream in, RectIdx rectIdx, DataAdapter adapter) throws IOException {
		this.in = in;
		this.rectIdx = rectIdx;
		this.adapter = adapter;
		init();
		parseData();
	}

	private void parseData() {
		// 获取所有sheet
		// int sheetsLen = wb.getNumberOfSheets();
		// for (int sheetIdx = 0; sheetIdx < sheetsLen; sheetIdx++) {
		// HSSFSheet sheet = wb.getSheetAt(sheetIdx);
		// parseSheet(sheetIdx, sheet);
		// }
		HSSFSheet sheet = wb.getSheetAt(0);
		parseSheet(0, sheet);
	}

	private void parseSheet(int sheetIdx, HSSFSheet sheet) {
		int lastRowNum = sheet.getLastRowNum();
		for (int rowIdx = (rectIdx != null && rectIdx.getTopIdx() != null ? rectIdx.getTopIdx() : 0); //
		rowIdx <= (rectIdx != null && rectIdx.getBottomIdx() != null ? rectIdx.getBottomIdx() : lastRowNum); //
		rowIdx++) {
			HSSFRow row = sheet.getRow(rowIdx);
			result.add(parseRow(sheetIdx, rowIdx, row));
		}
		errMsgs = adapter.setData(result);
	}

	private HashMap<String, Object> parseRow(int sheetIdx, int rowIdx, HSSFRow row) {
		short lastCellNum = row.getLastCellNum();
		HashMap<String, Object> result = new HashMap<>();
		for (int cellIdx = (rectIdx != null && rectIdx.getLeftIdx() != null ? rectIdx.getLeftIdx() : 0); //
		cellIdx <= (rectIdx != null && rectIdx.getRightIdx() != null ? rectIdx.getRightIdx() : lastCellNum); //
		cellIdx++) {
			HSSFCell cell = row.getCell(cellIdx);
			if (cell == null) {
				cell = row.createCell(cellIdx);
			}
			KeyValue kv = adapter.getColKeyValue(cellIdx, cell);
			if (kv == null || kv.getKey() == null)
				continue;
			result.put(kv.getKey(), kv.getValue());
			if (kv.Tag != null && kv.Tag instanceof KeyValue) {
				KeyValue k = (KeyValue) kv.Tag;
				result.put(k.getKey(), k.getValue());
			}
		}
		return result;
	}

	/**
	 * 初始化数据
	 *
	 * @throws IOException
	 */
	private void init() throws IOException {
		wb = new HSSFWorkbook(in);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new ImportExcel(//
				new FileInputStream("C:\\ShortPath\\poi\\src\\main\\resources\\学生数据模板-提案.xls")//
				, new RectIdx(0, 1, 10, null), new DataAdapter() {
					@Override
					public String setData(ArrayList<HashMap<String, Object>> result) {
						System.out.println(result);
						return null;
					}

					@Override
					public KeyValue getColKeyValue(int colIdx, HSSFCell cell) {
						KeyValue kv = new KeyValue();
						switch (colIdx) {
						case 0:
							kv.setKey("stu_no").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 1:
							kv.setKey("name").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 2:
							kv.setKey("age").setValue(Double.valueOf(getCellValue(cell).toString()).intValue());
							break;
						case 3:
							kv.setKey("address").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 4:
							kv.setKey("birthday").setValue(cell.getDateCellValue());
							break;
						case 5:
							kv.setKey("sex").setValue(getCellValue(cell));
							break;
						case 6:
							kv.setKey("parent_name").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 7:
							kv.setKey("parent_phone").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 8:
							kv.setKey("password").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 9:
							kv.setKey("ic_eid").setValue(String.valueOf(getCellValue(cell)));
							break;
						case 10:
							kv.setKey("ic_uid").setValue(String.valueOf(getCellValue(cell)));
							break;
						default:
							break;
						}
						return kv;
					}
				});
	}

	// public interface SheetAdapter {
	//
	// }
	//
	// public interface RowAdapter {
	//
	// }
	//
	// public interface CellAdapter {
	//
	// }

	/**
	 * 设置excel读取的索引区间
	 */
	public static class RectIdx {
		private Integer leftIdx;
		private Integer topIdx;
		private Integer rightIdx;
		private Integer bottomIdx;

		public Integer getLeftIdx() {
			return leftIdx;
		}

		public void setLeftIdx(Integer leftIdx) {
			this.leftIdx = leftIdx;
		}

		public Integer getTopIdx() {
			return topIdx;
		}

		public void setTopIdx(Integer topIdx) {
			this.topIdx = topIdx;
		}

		public Integer getRightIdx() {
			return rightIdx;
		}

		public void setRightIdx(Integer rightIdx) {
			this.rightIdx = rightIdx;
		}

		public Integer getBottomIdx() {
			return bottomIdx;
		}

		public void setBottomIdx(Integer bottomIdx) {
			this.bottomIdx = bottomIdx;
		}

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("RectIdx{");
			sb.append("leftIdx=").append(leftIdx);
			sb.append(", topIdx=").append(topIdx);
			sb.append(", rightIdx=").append(rightIdx);
			sb.append(", bottomIdx=").append(bottomIdx);
			sb.append('}');
			return sb.toString();
		}

		public RectIdx(Integer leftIdx, Integer topIdx, Integer rightIdx, Integer bottomIdx) {
			this.leftIdx = leftIdx;
			this.topIdx = topIdx;
			this.rightIdx = rightIdx;
			this.bottomIdx = bottomIdx;
		}
	}

	/**
	 * excel导入数据库适配器v0.1
	 */
	public abstract static class DataAdapter {
		/**
		 * 参数是生成的数据集合，可用于导入数据库
		 * 
		 * @param result
		 * @return error msg
		 */
		public abstract String setData(ArrayList<HashMap<String, Object>> result);

		/**
		 * 设置每一列的参数名和值
		 *
		 * @param colIdx
		 * @param cell
		 * @return
		 */
		public abstract KeyValue getColKeyValue(int colIdx, HSSFCell cell);

		/**
		 * 按照类型获取对应的值
		 *
		 * @param cell
		 * @return
		 */
		protected Object getCellValue(HSSFCell cell) {
			Object result = null;
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = null;
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
			case HSSFCell.CELL_TYPE_BOOLEAN:
			case HSSFCell.CELL_TYPE_ERROR:
				throw new IllegalStateException("暂不能处理的单元格类型");
			default:
				throw new IllegalStateException("暂不能处理的单元格类型");
			}
			return result;
		}
	}

	/**
	 * 单个键值对
	 */
	public static class KeyValue {
		private String key;
		private Object value;
		public Object Tag;

		@Override
		public String toString() {
			final StringBuffer sb = new StringBuffer("KeyValue{");
			sb.append("key='").append(key).append('\'');
			sb.append(", value=").append(value);
			sb.append('}');
			return sb.toString();
		}

		public String getKey() {
			return key;
		}

		public KeyValue setKey(String key) {
			this.key = key;
			return this;
		}

		public Object getValue() {
			return value;
		}

		public KeyValue setValue(Object value) {
			this.value = value;
			return this;
		}

		public KeyValue() {
		}

		public KeyValue(String key, Object value) {
			this.key = key;
			this.value = value;
		}
	}

	/**
	 * 获取错误信息
	 * 
	 * @return
	 */
	public String getErrMsgs() {
		return errMsgs;
	}
}
