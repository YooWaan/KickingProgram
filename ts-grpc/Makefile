
PROTOC_GEN_TS_PATH="./node_modules/.bin/protoc-gen-ts"

OUT_DIR=src/hello
PKG_DIR=pkg

PB_DIR=proto

pb-gen-ts:

  #  --js_out="import_style=commonjs,binary:${OUT_DIR}" \

	@rm -rf $(PB_DIR) && mkdir -p $(PB_DIR)
	npx grpc_tools_node_protoc \
    --plugin="protoc-gen-ts=$(PROTOC_GEN_TS_PATH)" \
		--js_out=import_style=commonjs,binary:$(PB_DIR) \
    --ts_out=service=grpc-web:$(PB_DIR) \
    pb/models/*.proto

	npx grpc_tools_node_protoc \
    --plugin="protoc-gen-ts=$(PROTOC_GEN_TS_PATH)" \
		--js_out=import_style=commonjs,binary:$(PB_DIR) \
    --ts_out=service=grpc-web:$(PB_DIR) \
    pb/server/*.proto

	@rm -rf $(OUT_DIR) && mkdir -p $(OUT_DIR)
	mv $(PB_DIR)/pb/models $(OUT_DIR)/
	mv $(PB_DIR)/pb/server $(OUT_DIR)/
	@rm -rf $(PB_DIR)

pb-gen-go:

	@rm -rf $(PB_DIR) && mkdir -p $(PB_DIR)

	protoc --go_out=$(PB_DIR) --go_opt=paths=source_relative \
    pb/models/*.proto

	protoc --go_out=$(PB_DIR) --go_opt=paths=source_relative \
		--go-grpc_out=$(PB_DIR) --go-grpc_opt=paths=source_relative \
    pb/server/*.proto

	@rm -rf $(PKG_DIR) && mkdir -p $(PKG_DIR)
	@mv $(PB_DIR)/pb/models $(PKG_DIR)/
	@mv $(PB_DIR)/pb/server $(PKG_DIR)/
	@rm -rf $(PB_DIR)
