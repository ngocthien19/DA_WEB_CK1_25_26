package vn.iotstar.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.iotstar.entity.DanhMuc;
import vn.iotstar.entity.SanPham;
import vn.iotstar.entity.DanhGia;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SanPhamSpecification {

	public static Specification<SanPham> filterProducts(
            DanhMuc danhMuc,
            List<String> prices,
            List<String> stores,
            List<String> loais,
            List<String> stars,
            String search) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // THÊM ĐIỀU KIỆN: CHỈ lấy sản phẩm đang hoạt động
            predicates.add(criteriaBuilder.isTrue(root.get("trangThai")));
            
            // THÊM ĐIỀU KIỆN: CHỈ lấy sản phẩm từ cửa hàng đang hoạt động
            predicates.add(criteriaBuilder.isTrue(root.get("cuaHang").get("trangThai")));
            
            // THÊM ĐIỀU KIỆN: CHỈ lấy sản phẩm từ danh mục đang hoạt động
            predicates.add(criteriaBuilder.isTrue(root.get("danhMuc").get("trangThai")));
            
            // Filter by category
            if (danhMuc != null) {
                predicates.add(criteriaBuilder.equal(root.get("danhMuc"), danhMuc));
            }

            // Filter by price ranges
            if (prices != null && !prices.isEmpty()) {
                List<Predicate> pricePredicates = new ArrayList<>();
                for (String range : prices) {
                    switch (range) {
                        case "below10":
                            pricePredicates.add(criteriaBuilder.lessThan(
                                root.get("giaBan"), new BigDecimal("10000000")));
                            break;
                        case "1015":
                            pricePredicates.add(criteriaBuilder.between(
                                root.get("giaBan"), 
                                new BigDecimal("10000000"), 
                                new BigDecimal("15000000")));
                            break;
                        case "1520":
                            pricePredicates.add(criteriaBuilder.between(
                                root.get("giaBan"), 
                                new BigDecimal("15000000"), 
                                new BigDecimal("20000000")));
                            break;
                        case "2025":
                            pricePredicates.add(criteriaBuilder.between(
                                root.get("giaBan"), 
                                new BigDecimal("20000000"), 
                                new BigDecimal("25000000")));
                            break;
                        case "above25":
                            pricePredicates.add(criteriaBuilder.greaterThanOrEqualTo(
                                root.get("giaBan"), new BigDecimal("25000000")));
                            break;
                    }
                }
                predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[0])));
            }

            // Filter by stores
            if (stores != null && !stores.isEmpty()) {
                predicates.add(root.get("cuaHang").get("tenCuaHang").in(stores));
            }

            // Filter by product types
            if (loais != null && !loais.isEmpty()) {
                predicates.add(root.get("loaiSanPham").in(loais));
            }

            // Filter by star ratings - TÍNH TOÁN TỪ BẢNG DanhGia
            if (stars != null && !stars.isEmpty()) {
                List<Predicate> starPredicates = new ArrayList<>();
                
                // Tạo subquery để tính số sao trung bình từ bảng DanhGia
                Subquery<Double> avgRatingSubquery = query.subquery(Double.class);
                Root<DanhGia> danhGiaRoot = avgRatingSubquery.from(DanhGia.class);
                avgRatingSubquery.select(criteriaBuilder.avg(danhGiaRoot.get("soSao")));
                avgRatingSubquery.where(criteriaBuilder.equal(danhGiaRoot.get("sanPham"), root));
                
                for (String s : stars) {
                    switch (s) {
                        case "0":
                            // Sản phẩm chưa có đánh giá hoặc số sao trung bình = 0
                            starPredicates.add(criteriaBuilder.or(
                                criteriaBuilder.isNull(avgRatingSubquery),
                                criteriaBuilder.lessThan(
                                    criteriaBuilder.coalesce(avgRatingSubquery, 0.0), 
                                    1.0
                                )
                            ));
                            break;
                        case "12":
                            // Số sao trung bình từ 1 đến dưới 3 (làm tròn = 1 hoặc 2)
                            starPredicates.add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 1.0),
                                criteriaBuilder.lessThan(avgRatingSubquery, 2.5)
                            ));
                            break;
                        case "23":
                            // Số sao trung bình từ 2.5 đến dưới 3.5 (làm tròn = 3)
                            starPredicates.add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 2.5),
                                criteriaBuilder.lessThan(avgRatingSubquery, 3.5)
                            ));
                            break;
                        case "34":
                            // Số sao trung bình từ 3.5 đến dưới 4.5 (làm tròn = 4)
                            starPredicates.add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 3.5),
                                criteriaBuilder.lessThan(avgRatingSubquery, 4.5)
                            ));
                            break;
                        case "45":
                            // Số sao trung bình từ 4.5 đến 5 (làm tròn = 5)
                            starPredicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 4.5)
                            );
                            break;
                    }
                }
                predicates.add(criteriaBuilder.or(starPredicates.toArray(new Predicate[0])));
            }

            // Search by product name
            if (search != null && !search.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("tenSanPham")),
                    "%" + search.toLowerCase().trim() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Specification mới cho trang tất cả sản phẩm - chỉ lấy từ danh mục đang hoạt động
     * Được sử dụng trong phương thức allProducts() của HomeController
     */
    public static Specification<SanPham> filterProductsWithActiveCategories(
            DanhMuc danhMuc,
            List<String> prices,
            List<String> stores,
            List<String> loais,
            List<String> stars,
            String search) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // THÊM ĐIỀU KIỆN: CHỈ lấy sản phẩm đang hoạt động
            predicates.add(criteriaBuilder.isTrue(root.get("trangThai")));
            
            // THÊM ĐIỀU KIỆN: CHỈ lấy sản phẩm từ cửa hàng đang hoạt động
            predicates.add(criteriaBuilder.isTrue(root.get("cuaHang").get("trangThai")));
            
            // THÊM ĐIỀU KIỆN: CHỈ lấy sản phẩm từ danh mục đang hoạt động
            predicates.add(criteriaBuilder.isTrue(root.get("danhMuc").get("trangThai")));
            
            // Filter by category (nếu có)
            if (danhMuc != null) {
                predicates.add(criteriaBuilder.equal(root.get("danhMuc"), danhMuc));
            }

            // Filter by price ranges
            if (prices != null && !prices.isEmpty()) {
                List<Predicate> pricePredicates = new ArrayList<>();
                for (String range : prices) {
                    switch (range) {
                        case "below10":
                            pricePredicates.add(criteriaBuilder.lessThan(
                                root.get("giaBan"), new BigDecimal("10000000")));
                            break;
                        case "1015":
                            pricePredicates.add(criteriaBuilder.between(
                                root.get("giaBan"), 
                                new BigDecimal("10000000"), 
                                new BigDecimal("15000000")));
                            break;
                        case "1520":
                            pricePredicates.add(criteriaBuilder.between(
                                root.get("giaBan"), 
                                new BigDecimal("15000000"), 
                                new BigDecimal("20000000")));
                            break;
                        case "2025":
                            pricePredicates.add(criteriaBuilder.between(
                                root.get("giaBan"), 
                                new BigDecimal("20000000"), 
                                new BigDecimal("25000000")));
                            break;
                        case "above25":
                            pricePredicates.add(criteriaBuilder.greaterThanOrEqualTo(
                                root.get("giaBan"), new BigDecimal("25000000")));
                            break;
                    }
                }
                predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[0])));
            }

            // Filter by stores
            if (stores != null && !stores.isEmpty()) {
                predicates.add(root.get("cuaHang").get("tenCuaHang").in(stores));
            }

            // Filter by product types
            if (loais != null && !loais.isEmpty()) {
                predicates.add(root.get("loaiSanPham").in(loais));
            }

            // Filter by star ratings - TÍNH TOÁN TỪ BẢNG DanhGia
            if (stars != null && !stars.isEmpty()) {
                List<Predicate> starPredicates = new ArrayList<>();
                
                // Tạo subquery để tính số sao trung bình từ bảng DanhGia
                Subquery<Double> avgRatingSubquery = query.subquery(Double.class);
                Root<DanhGia> danhGiaRoot = avgRatingSubquery.from(DanhGia.class);
                avgRatingSubquery.select(criteriaBuilder.avg(danhGiaRoot.get("soSao")));
                avgRatingSubquery.where(criteriaBuilder.equal(danhGiaRoot.get("sanPham"), root));
                
                for (String s : stars) {
                    switch (s) {
                        case "0":
                            // Sản phẩm chưa có đánh giá hoặc số sao trung bình = 0
                            starPredicates.add(criteriaBuilder.or(
                                criteriaBuilder.isNull(avgRatingSubquery),
                                criteriaBuilder.lessThan(
                                    criteriaBuilder.coalesce(avgRatingSubquery, 0.0), 
                                    1.0
                                )
                            ));
                            break;
                        case "12":
                            // Số sao trung bình từ 1 đến dưới 3 (làm tròn = 1 hoặc 2)
                            starPredicates.add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 1.0),
                                criteriaBuilder.lessThan(avgRatingSubquery, 2.5)
                            ));
                            break;
                        case "23":
                            // Số sao trung bình từ 2.5 đến dưới 3.5 (làm tròn = 3)
                            starPredicates.add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 2.5),
                                criteriaBuilder.lessThan(avgRatingSubquery, 3.5)
                            ));
                            break;
                        case "34":
                            // Số sao trung bình từ 3.5 đến dưới 4.5 (làm tròn = 4)
                            starPredicates.add(criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 3.5),
                                criteriaBuilder.lessThan(avgRatingSubquery, 4.5)
                            ));
                            break;
                        case "45":
                            // Số sao trung bình từ 4.5 đến 5 (làm tròn = 5)
                            starPredicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(avgRatingSubquery, 4.5)
                            );
                            break;
                    }
                }
                predicates.add(criteriaBuilder.or(starPredicates.toArray(new Predicate[0])));
            }

            // Search by product name
            if (search != null && !search.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("tenSanPham")),
                    "%" + search.toLowerCase().trim() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Specification cho tìm kiếm sản phẩm theo tên (cho admin hoặc tìm kiếm nâng cao)
     */
    public static Specification<SanPham> hasTenSanPhamContaining(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("tenSanPham")),
                "%" + keyword.toLowerCase() + "%"
            );
        };
    }
}