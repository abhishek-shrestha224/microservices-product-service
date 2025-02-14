package world.hello.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import world.hello.product.domain.model.ProductModel;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String> {}
