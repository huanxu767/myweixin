package com.xh.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.SSLException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
/**
 * 
 * @author xwtec
 	*GET,HEAD,  POST,  PUT,  DELETE,  TRACEand OPTIONS. There is a specific class for each method type.:  HttpGet,
	HttpHead, HttpPost, HttpPut, HttpDelete, HttpTrace, and HttpOptions.
 */
public class TheSimplestFormHttpClient {
	/**
	 * 使用get方式请求
	 * 
	 * @throws IOException
	 */
	public  static void getExample() throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx6b14095999d2676d&secret=efdae94708b193b4f83a0820f25141f5");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			Header header= entity.getContentType();
			System.out.println(header.getName());
			System.out.println(header.getValue());
			InputStream instream = entity.getContent();
			Long len = entity.getContentLength();
			if (len != -1 && len < 2048) {
				System.out.println(EntityUtils.toString(entity));
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line+"\n");
				}
				String reqJSON = sb.toString();
				System.out.println(reqJSON);
			}
		}
	}
	/**
	 * 使用get方式请求 
	 * HTTPClient provides URIBuilder utility class to simplify creation and modification of request URIs
	 * 
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	public  static void getExample2() throws IOException, URISyntaxException {
		HttpClient httpclient = new DefaultHttpClient();
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("127.0.0.1:8080").setPath("/HttpClientStudy/httpclient/getMes")
		.setParameter("name","xuhuan").setParameter("love", "中文_zhongwen");
		URI uri = builder.build();
		System.out.println("组装的URI:"+uri.toString());
		HttpGet httpget = new HttpGet(uri);
		System.out.println("HttpGet组装的URI:"+httpget.getURI());
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			Header header= entity.getContentType();
			System.out.println(header.getName());
			System.out.println(header.getValue());
			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					instream, "UTF-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
			}
			String reqJSON = sb.toString();
			System.out.println(reqJSON);
		}
	}
/**
 * 1.1.2. HTTP response
 * HTTP response is a message sent by the server back to the client after having received and interpreted
	a request message. The first line of that message consists of the protocol version followed by a numeric
	status code and its associated textual phrase.
	中文翻译：
	HTTP响应是由服务器发送的消息返回给客户端后，接收和解释
	的请求消息。该消息包含的第一行后面跟随一个数字的协议版本
	状态代码及其相关的文本短语。
 */
	public static void httpResponseTest() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
				HttpStatus.SC_OK, "OK");
		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());
	}
	/**
	 * 1.1.3. Working with message headers
	 * An HTTP message can contain a number of headers describing properties of the message such as
		the content length, content type and so on. HttpClient provides methods to retrieve, add, remove and
		enumerate headers.
	 */
	public static void httpResponseTest2() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
		Header h1 = response.getFirstHeader("Set-Cookie");
		System.out.println(h1);
		Header h2 = response.getLastHeader("Set-Cookie");
		System.out.println(h2);
		Header[] hs = response.getHeaders("Set-Cookie");
		for (int i = 0; i < hs.length; i++) {
			Header head=hs[i];
			System.out.println("循环"+i+"  name:"+head.getName()+"  value:"+head.getValue());
		}
		System.out.println(hs.length);	
		//The most efficient way to obtain all headers of a given type is by using the HeaderIteratorinterface. begin
		System.out.println("Iterater循环:");
		HeaderIterator iterater=response.headerIterator("Set-Cookie");
		while (iterater.hasNext()) {
			Header headTemp = iterater.nextHeader();
			System.out.println("  name:"+headTemp.getName()+"  value:"+headTemp.getValue());
		}
		// The most efficient way to obtain all headers of a given type is by
		// using the HeaderIteratorinterface. end
		
		//It also provides convenience methods to parse HTTP messages into individual header elements. begin
		System.out.println("It also provides convenience methods to parse HTTP messages into individual header elements.");
		HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
		while (it.hasNext()) {
			HeaderElement elem = it.nextElement();
			System.out.println(elem.getName() + " = " + elem.getValue());
			NameValuePair[] params = elem.getParameters();
			for (int i = 0; i < params.length; i++) {
				System.out.println(" " + params[i]);
			}
		}
		//It also provides convenience methods to parse HTTP messages into individual header elements. end
	}

	/**
	 * 1.1.4.2. Using HTTP entities
	 */
	public static void StringEntityTest() throws ParseException, IOException{
		StringEntity myEntity = new StringEntity("important message",
				ContentType.create("text/plain", "UTF-8"));
		System.out.println(myEntity.getContentType());
		System.out.println(myEntity.getContentLength());
		System.out.println(EntityUtils.toString(myEntity));
		System.out.println(EntityUtils.toByteArray(myEntity));
	}
	/**
	 * 1.1.5. Ensuring release of low level resources
	 * In order to ensure proper release of system resources one must close the content stream associated
		with the entity.
		
	 */
	public static void modelReleaseResources() throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes?name=xuhuan&age=22&love=电影");
		HttpResponse response;
		response = httpclient.execute(httpget);
			HttpEntity entity= response.getEntity();
			if(entity!=null){
				InputStream instream = entity.getContent();
				try {
					//do something userful
				} finally{
					instream.close();
				}
			}
	}
	/** 1.1.5. 
	 * When working with streaming entities, one can use the EntityUtils#consume(HttpEntity)method
		to ensure that the entity content has been fully consumed and the underlying stream has been closed.
		There can be situations, however, when only a small portion of the entire response content needs
		to be retrieved and the performance penalty for consuming the remaining content and making the
		connection reusable is too high, in which case one can simply terminate the request by calling
		HttpUriRequest#abort()method.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	*/
	public static void OtherReleaseResourse() throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes?name=xuhuan&age=22&love=电影");
		HttpResponse reponse = httpClient.execute(httpGet);
		HttpEntity entity = reponse.getEntity();
		if(entity!=null){
			InputStream istream = entity.getContent();
			int byeOne = istream.read();
			int byeTwo = istream.read();
			//DO not need the rest
			httpGet.abort();
		}
	}
	/**
	 * In some situations it may be necessary to be able to read entity content more than once. In this case
		entity content must be buffered in some way, either in memory or on disk. The simplest way to
		accomplish that is by wrapping the original entity with the BufferedHttpEntityclass. This will cause
		the content of the original entity to be read into a in-memory buffer. In all other ways the entity wrapper
		will be have the original one.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void bufferedHttpEntityStudy() throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://localhost/");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			entity = new BufferedHttpEntity(entity);
		}
	}
	/**
	 * 1.1.7. Producing entity content
	 * HttpClient provides several classes that can be used to efficiently stream out content though HTTP
		connections. Instances of those classes can be associated with entity enclosing requests such as
		POSTand  PUTin order to enclose entity content into outgoing HTTP requests. HttpClient provides
		several classes for most common data containers such as string, byte array, input stream, and file:
		StringEntity, ByteArrayEntity, InputStreamEntity, and FileEntity.
	 */
	public static void fileEntityStudy(){
		File file = new File("c:\\xx.txt");
		FileEntity fileEntity = new FileEntity(file,ContentType.create("text/plain","UTF-8"));
		HttpPost httppost = new HttpPost("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes?name=xuhuan&age=22&love=电影");
		httppost.setEntity(fileEntity);
	}
	/**
	 * 1.1.7.1. HTML forms
	 * Many applications need to simulate the process of submitting an HTML form, for instance, in
		order to log in to a web application or submit input data. HttpClient provides the entity class
		UrlEncodedFormEntityto facilitate the process.
		The UrlEncodedFormEntityinstance will use the so called URL encoding to encode parameters and
		produce the following content:
		param1=value1&param2=value2
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void simulateFormStudy() throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("name", "xuhuan许欢"));
		formparams.add(new BasicNameValuePair("password", "123321"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,"UTF-8");
		HttpPost httppost = new HttpPost("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes");
		httppost.setEntity(entity);
		System.out.println(httppost.toString());
		httpclient.execute(httppost);
	}
	/**
	 * 1.1.7.2. Content chunking
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static void contentChunkingStudy() throws UnsupportedEncodingException{
		StringEntity entity = new StringEntity("important message", "text/plain; charset=\"UTF-8\"");
		entity.setChunked(true);
		HttpPost httppost = new HttpPost("http://localhost/acrtion.do");
		httppost.setEntity(entity);
	}
	/**
	 * 1.1.8. Response handlers
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void ResponseHandlersStudy() throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes");
		ResponseHandler<String> handler = new ResponseHandler<String>(){
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity!=null) {
					return EntityUtils.toString(entity);
				}else{
					return null;
				}
			}
		};
		String result = httpClient.execute(httpGet, handler);
		System.out.println(result);
	}
	/**
	 * 1.2. HTTP execution context
	 */
	public static void httpContextStudy() throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localHttpContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes");
		HttpResponse response = httpClient.execute(httpget,localHttpContext);
		HttpHost target = (HttpHost)localHttpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		System.out.println("Final target:"+target);
		HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
	}
	/**
	 * 1.3. Exception handling
	 * 1.3.4. Request retry handler
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void httpRequestRetryHandlerStudy() throws ClientProtocolException, IOException{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				System.out.println("retry times:"+executionCount);
				if (executionCount>=5) {
					//Do not retry if over max retry count
					System.out.println("Do not retry if over max retry count");
					return false;
				}
				if(exception instanceof InterruptedIOException){
					//Timeout
					System.out.println("timeout");
					return false;
				}
				if(exception instanceof UnknownHostException){
					//Unknown host
					System.out.println("unknow host");
					return false;
				}
				if(exception instanceof ConnectException){
					// Connection refused
					System.out.println("Connection refused");
					return false;
				}
				if (exception instanceof SSLException) {
					// SSL handshake exception
					System.out.println("SSL handshake exception");
					return false;
				}
				HttpRequest request=(HttpRequest)context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if(idempotent){
					// Retry if the request is considered idempotent 
					System.out.println("retry if the request is considered idempotent");
					return true;
				}
				return false;
			}
		};
		httpclient.setHttpRequestRetryHandler(myRetryHandler);
		HttpGet httpget = new HttpGet("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
	}
	/**
	 * 1.5. HTTP protocol interceptors
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void HttpProtocolInterceptorsStudy() throws ClientProtocolException, IOException{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		AtomicInteger count = new AtomicInteger(1);
		localContext.setAttribute("count", count);
		httpclient.addRequestInterceptor(new HttpRequestInterceptor(){

			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				AtomicInteger count = (AtomicInteger) context.getAttribute("count");
				System.out.println(count.get());
				request.addHeader("Count",Integer.toString(count.getAndIncrement()));
			}
			
		});
		HttpGet httpget = new HttpGet("http://127.0.0.1:8080/HttpClientStudy/httpclient/getMes");
		for (int i = 0; i < 10; i++) {
			HttpResponse response = httpclient.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
		}
	}
	/**
	 * 1.6.1. Parameter hierarchies
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void parameterHierarchiesStudy() throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0); // Default to HTTP 1.0
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		HttpGet httpget = new HttpGet("http://www.baidu.com");
		httpget.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1); // Use HTTP 1.1 for this request only
		httpget.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
			public void process(final HttpRequest request,
					final HttpContext context) throws HttpException,
					IOException {
				System.out.println(request.getParams().getParameter(
						CoreProtocolPNames.PROTOCOL_VERSION));
				System.out.println(request.getParams().getParameter(
						CoreProtocolPNames.HTTP_CONTENT_CHARSET));
				System.out.println(request.getParams().getParameter(
						CoreProtocolPNames.USE_EXPECT_CONTINUE));
				System.out.println(request.getParams().getParameter(
						CoreProtocolPNames.STRICT_TRANSFER_ENCODING));
			}
		});
		httpclient.execute(httpget);
	}
	/**
	 * 1.6.2. HTTP parameters beans
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void HttpParametersBeanStudy() throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset("UTF-8");
		paramsBean.setUseExpectContinue(true);
		System.out.println(params.getParameter(CoreProtocolPNames.PROTOCOL_VERSION));
		System.out.println(params.getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET));
		System.out.println(params.getParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE));
		System.out.println(params.getParameter(CoreProtocolPNames.USER_AGENT));
		httpclient.setParams(params);
		HttpGet httpget = new HttpGet("http://www.baidu.com");
		httpclient.execute(httpget);
		
	}
	
	public static void main(String[] args) {
		try {
//			HttpParametersBeanStudy();
			getExample();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
