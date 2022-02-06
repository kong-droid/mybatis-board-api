package shop.zeedeco.controller.product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.product.ProductDetailDto;
import shop.zeedeco.dto.product.ProductDto;
import shop.zeedeco.service.product.ProductService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public ProductDto.ViewProductsRes getProducts (
			@RequestBody @Valid final ProductDto.ViewProductReq req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) throws Exception {
		Map<String, Object> responseMap = productService.getProducts(req.toMap(), page, size);
		List<Map<String, Object>> products = (List<Map<String, Object>>) responseMap.get("products");
		Integer totalCount = (Integer) responseMap.get("totalCount");
		return new ProductDto.ViewProductsRes(products.stream().map(ProductDto.ViewProductRes::new).collect(Collectors.toList()), totalCount);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{productSeq}")
	public ProductDto.ViewProductRes getProduct (@PathVariable @Valid int productSeq) throws Exception {
		Map<String, Object> responseMap = productService.getProduct(productSeq);
		return new ProductDto.ViewProductRes(responseMap);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addProduct(@RequestBody @Valid final ProductDto.AddProductReq req) throws Exception {
		this.productService.addProduct(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setProduct(@RequestBody @Valid final ProductDto.SetProductReq req) throws Exception {
		this.productService.setProduct(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void logicalRemoveProduct(@RequestBody @Valid final ProductDto.SetProductByDelYn req) throws Exception {
		this.productService.logicalRemoveProduct(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{productSeq}")
	public void physicalRemoveProduct(@PathVariable @Valid int productSeq) throws Exception {
		this.productService.physicalRemoveProduct(productSeq);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/detail")
	public void addProductDetail(@RequestBody @Valid final ProductDetailDto.AddProductDetail req) throws Exception {
		this.productService.addProductDetail(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/detail")
	public void setProductDetail(@RequestBody @Valid final ProductDetailDto.SetProductDetail req) throws Exception {
		this.productService.setProductDetail(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/detail")
	public void logicalRemoveProductDetail(@RequestBody @Valid final ProductDetailDto.SetProductDetailByDelYn req) throws Exception {
		this.productService.logicalRemoveProductDetail(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/detail/{productDetailSeq}")
	public void physicalRemoveProductDetail(@PathVariable @Valid int productDetailSeq) throws Exception {
		this.productService.physicalRemoveProductDetail(productDetailSeq);
	}
}
