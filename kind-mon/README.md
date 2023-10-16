


## Command

```sh
# create cluster
kind create cluster --config kind/cluster.yaml --name prom-metrics

# create promtheus
kubectl apply -f k8s/prom-config.yaml
kubectl apply -f k8s/prom.yaml

# create grafana


# kube-meta-metrics
```


## Reference

- [Kind prometheus](https://yokaze.github.io/2021/03/07/)
- [Kind grafana](https://yokaze.github.io/2021/03/08/)
- [Kubernetes のメトリクスを Prometheus を使って監視する](https://qiita.com/kkohtaka/items/59007f0ada56d9f9a8f4)
- [Kubernetesクラスタにkube-state-metricsとcAdvisorをぶちこんでPrometheusで監視する](https://uzimihsr.github.io/post/2022-11-28-kubernetes-prometheus-kube-state-metrics-cadvisor-ja/)