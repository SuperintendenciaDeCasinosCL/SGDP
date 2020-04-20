package cl.gob.scj.sgdp.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import cl.gob.scj.sgdp.util.pagination.*;

public class DataTableRequestDTO {

	/** The unique id. */
	private String uniqueId;

	/** The draw. */
	private String draw;

	/** The start. */
	private Integer start;

	/** The length. */
	private Integer length;

	/** The search. */
	private String search;

	/** The regex. */
	private boolean regex;

	/** The columns. */
	private List<DataTableColumnSpecsDTO> columns;

	/** The order. */
	private DataTableColumnSpecsDTO order;

	/** The is global search. */
	private boolean isGlobalSearch;

	public DataTableRequestDTO(HttpServletRequest request) {
		prepareDataTableRequestDTO(request);
	}

	
	
	public DataTableRequestDTO() {
		super();
	}



	/**
	 * Prepare data table request.
	 *
	 * @param request
	 *            the request
	 */
	private void prepareDataTableRequestDTO(HttpServletRequest request) {

		Enumeration<String> parameterNames = request.getParameterNames();

		if (parameterNames.hasMoreElements()) {

			this.setStart(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_NO)));
			this.setLength(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_SIZE)));
			this.setUniqueId(request.getParameter("_"));
			this.setDraw(request.getParameter(PaginationCriteria.DRAW));

			this.setSearch(request.getParameter("search[value]"));
			this.setRegex(Boolean.valueOf(request.getParameter("search[regex]")));

			int sortableCol = Integer.parseInt(request.getParameter("order[0][column]"));

			List<DataTableColumnSpecsDTO> columns = new ArrayList<DataTableColumnSpecsDTO>();

			if (!AppUtil.isObjectEmpty(this.getSearch())) {
				this.setGlobalSearch(true);
			}

			maxParamsToCheck = getNumberOfColumns(request);

			for (int i = 0; i < maxParamsToCheck; i++) {
				if (null != request.getParameter("columns[" + i + "][data]")
						&& !"null".equalsIgnoreCase(request.getParameter("columns[" + i + "][data]"))
						&& !AppUtil.isObjectEmpty(request.getParameter("columns[" + i + "][data]"))) {
					DataTableColumnSpecsDTO colSpec = new DataTableColumnSpecsDTO(request, i);
					if (i == sortableCol) {
						this.setOrder(colSpec);
					}
					columns.add(colSpec);

					if (!AppUtil.isObjectEmpty(colSpec.getSearch())) {
						this.setGlobalSearch(false);
					}
				}
			}

			if (!AppUtil.isObjectEmpty(columns)) {
				this.setColumns(columns);
			}
		}
	}

	private int getNumberOfColumns(HttpServletRequest request) {
		Pattern p = Pattern.compile("columns\\[[0-9]+\\]\\[data\\]");
		@SuppressWarnings("rawtypes")
		Enumeration params = request.getParameterNames();
		List<String> lstOfParams = new ArrayList<String>();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			Matcher m = p.matcher(paramName);
			if (m.matches()) {
				lstOfParams.add(paramName);
			}
		}
		return lstOfParams.size();
	}

	/**
	 * Gets the pagination request.
	 *
	 * @return the pagination request
	 */
	public PaginationCriteria getPaginationRequest() {

		PaginationCriteria pagination = new PaginationCriteria();
		pagination.setPageNumber(this.getStart());
		pagination.setPageSize(this.getLength());

		SortBy sortBy = null;
		if (!AppUtil.isObjectEmpty(this.getOrder())) {
			sortBy = new SortBy();
			sortBy.addSort(this.getOrder().getData(), SortOrder.fromValue(this.getOrder().getSortDir()));
		}

		FilterBy filterBy = new FilterBy();
		filterBy.setGlobalSearch(this.isGlobalSearch());
		for (DataTableColumnSpecsDTO colSpec : this.getColumns()) {
			if (colSpec.isSearchable()) {
				if (!AppUtil.isObjectEmpty(this.getSearch()) || !AppUtil.isObjectEmpty(colSpec.getSearch())) {
					filterBy.addFilter(colSpec.getData(),
							(this.isGlobalSearch()) ? this.getSearch() : colSpec.getSearch());
				}
			}
		}

		pagination.setSortBy(sortBy);
		pagination.setFilterBy(filterBy);

		return pagination;
	}

	/** The max params to check. */
	private int maxParamsToCheck = 0;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public boolean isRegex() {
		return regex;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	public List<DataTableColumnSpecsDTO> getColumns() {
		return columns;
	}

	public void setColumns(List<DataTableColumnSpecsDTO> columns) {
		this.columns = columns;
	}

	public DataTableColumnSpecsDTO getOrder() {
		return order;
	}

	public void setOrder(DataTableColumnSpecsDTO order) {
		this.order = order;
	}

	public boolean isGlobalSearch() {
		return isGlobalSearch;
	}

	public void setGlobalSearch(boolean isGlobalSearch) {
		this.isGlobalSearch = isGlobalSearch;
	}

}
