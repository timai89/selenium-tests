package com.wikia.webdriver.TestCases.SearchTests;

import java.util.List;

import org.testng.annotations.Test;

import com.wikia.webdriver.Common.ContentPatterns.URLsContent;
import com.wikia.webdriver.Common.Core.URLBuilder.UrlBuilder;
import com.wikia.webdriver.Common.DataProvider.IntraWikiSearchProvider;
import com.wikia.webdriver.Common.Templates.NewTestTemplate;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Search.IntraWikiSearch.IntraWikiSearchPageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Search.IntraWikiSearch.IntraWikiSearchPageObject.sortOptions;

public class IntraWikiSearch extends NewTestTemplate {

	private String testedWiki;

	public IntraWikiSearch() {
		UrlBuilder urlBuilder = new UrlBuilder(config.getEnv());
		testedWiki = urlBuilder.getUrlForWiki("muppet");
	}

	private static final int resultsPerPage = 25;
	private static final String searchPhraseResults = "a";
	private static final String searchPaginationResults = "what";
	private static final String searchResultWithExtension = "betweenlions";
	private static final String searchPhraseNoResults = "qazwsxedcrfvtgb";
	private static final String searchPhraseSuggestions = "Gon";

	@Test(dataProviderClass=IntraWikiSearchProvider.class,
			dataProvider="getArticleName",
			groups={"intraSearch001", "intraSearch", "Search"}
	)
	public void intraWikiSearch_001_exactMatch(String query) {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(query);
		search.verifyFirstResult(query);
	}

	@Test(groups={"intraSearch002", "intraSearch", "Search"})
	public void intraWikiSearch_002_pagination() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPaginationResults);
		String firstResult = search.getTitleInnerText();
		search.verifyPagination();
		search.clickNextPaginator();
		search.verifyArticlesNotTheSame(firstResult);
		search.verifyPagination();
		search.clickPrevPaginator();
		search.verifyArticlesTheSame(firstResult);
		search.verifyPagination();
		search.verifyLastResultPage();
	}

	@Test(groups={"intraSearch003", "intraSearch", "Search"})
	public void intraWikiSearch_003_resultsCount() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.verifyResultsCount(resultsPerPage);
		search.clickNextPaginator();
		search.verifyResultsCount(resultsPerPage);
	}

	@Test(groups={"intraSearch004", "intraSearch", "Search"})
	public void intraWikiSearch_004_noResults() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseNoResults);
		search.verifyNoResults();
	}

	@Test(groups={"intraSearch005", "intraSearch", "Search"})
	public void intraWikiSearch_005_filtering() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.selectPhotosVideos();
		search.verifyNamespacesInTitles(URLsContent.fileNameSpace);
		search.selectPhotosOnly();
		search.verifyNamespacesInTitles(URLsContent.fileNameSpace);
		search.verifyAllResultsImages(resultsPerPage);
		search.selectVideosOnly();
		search.verifyNamespacesInTitles(URLsContent.fileNameSpace);
		search.verifyAllResultsVideos(resultsPerPage);
	}

	@Test(groups={"intraSearch006", "intraSearch", "Search"})
	public void intraWikiSearch_006_sortingVideos() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.selectPhotosVideos();
		search.selectVideosOnly();
		search.verifyNamespacesInTitles(URLsContent.fileNameSpace);
		search.sortBy(sortOptions.duration);
		List<String> titles1 = search.getTitles();
		search.sortBy(sortOptions.relevancy);
		List<String> titles2 = search.getTitles();
		search.sortBy(sortOptions.publishDate);
		List<String> titles3 = search.getTitles();
		search.compareTitleListsNotEquals(titles1, titles2);
		search.compareTitleListsNotEquals(titles1, titles3);
		search.compareTitleListsNotEquals(titles2, titles3);
	}

	@Test(groups={"intraSearch007", "intraSearch", "Search"})
	public void intraWikiSearch_007_sortingImages() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.selectPhotosVideos();
		search.selectPhotosOnly();
		search.verifyNamespacesInTitles(URLsContent.fileNameSpace);
		search.sortBy(sortOptions.relevancy);
		List<String> titles1 = search.getTitles();
		search.sortBy(sortOptions.publishDate);
		List<String> titles2 = search.getTitles();
		search.compareTitleListsNotEquals(titles1, titles2);
	}

	@Test(groups={"intraSearch008", "intraSearch", "Search"})
	public void intraWikiSearch_008_dropDownSuggestions() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.typeSearchQuery(searchPhraseSuggestions);
		search.verifySuggestions(searchPhraseSuggestions);
	}

	@Test(groups={"intraSearch009", "intraSearch", "Search"})
	public void intraWikiSearch_009_languageTranslation() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.addQqxUselang();
		search.verifyLanguageTranslation();
	}

	@Test(groups={"intraSearch010", "intraSearch", "Search"})
	public void intraWikiSearch_010_imagesAndVideosOnly() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.selectPhotosVideos();
		search.selectPhotosOnly();
		search.verifyPhotosOnly();
		search.selectVideosOnly();
		search.verifyVideosOnly();
	}

	@Test(groups={"intraSearch011", "intraSearch", "Search"})
	public void intraWikiSearch_011_defaultNamespaces() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhraseResults);
		search.clickAdvancedButton();
		search.verifyDefaultNamespaces();
	}

	@Test(groups={"intraSearch012", "intraSearch", "Search"})
	public void intraWikiSearch_012_extensionNotNeeded() {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchResultWithExtension);
		search.selectPhotosVideos();
		search.verifyFirstResultExtension(searchResultWithExtension);
	}

	@Test(dataProviderClass=IntraWikiSearchProvider.class,
			dataProvider="getNamespaces",
			groups={"intraSearch013", "intraSearch", "Search"}
	)
	public void intraWikiSearch_013_namespaces(String searchPhrase, String namespace) {
		IntraWikiSearchPageObject search = new IntraWikiSearchPageObject(driver, testedWiki);
		search.searchFor(searchPhrase);
		search.selectAllAdvancedOptions();
		search.verifyNamespace(namespace);
	}

}
