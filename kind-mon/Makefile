
KUBE_STATE=k8s/kube-state-metrics
KUBE_RAW_STD=https://raw.githubusercontent.com/kubernetes/kube-state-metrics/master/examples/standard


dl-kube-state-metrics:
	@rm -rf $(KUBE_STATE) && mkdir -p $(KUBE_STATE)
	curl -o $(KUBE_STATE)/sa.yaml $(KUBE_RAW_STD)/service-account.yaml
	curl -o $(KUBE_STATE)/role.yaml $(KUBE_RAW_STD)/cluster-role.yaml
	curl -o $(KUBE_STATE)/role-binding.yaml $(KUBE_RAW_STD)/cluster-role-binding.yaml
	curl -o $(KUBE_STATE)/deployment.yaml $(KUBE_RAW_STD)/deployment.yaml
	curl -o $(KUBE_STATE)/service.yaml $(KUBE_RAW_STD)/service.yaml


apply-kube-state-metrics:
	kubectl apply -f $(KUBE_STATE)/sa.yaml
	kubectl apply -f $(KUBE_STATE)/role.yaml
	kubectl apply -f $(KUBE_STATE)/role-binding.yaml
	kubectl apply -f $(KUBE_STATE)/deployment.yaml
	kubectl apply -f $(KUBE_STATE)/service.yaml
