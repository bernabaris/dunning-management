CREATE OR REPLACE PROCEDURE GET_DUNNING_LEVEL (
    p_invoice_id IN NUMBER,
    p_dunning_level OUT VARCHAR2
)
AS
    v_due_date DATE;
    v_status VARCHAR2(50);
    v_overdue_days NUMBER;
BEGIN
SELECT due_date, status
INTO v_due_date, v_status
FROM invoice
WHERE id = p_invoice_id;

IF v_status = 'PAID' THEN
        p_dunning_level := 'PAID';
        RETURN;
END IF;

    v_overdue_days := TRUNC(SYSDATE) - TRUNC(v_due_date);

    IF v_overdue_days <= 7 THEN
        p_dunning_level := 'NONE';
    ELSIF v_overdue_days <= 15 THEN
        p_dunning_level := 'LEVEL_1';
    ELSIF v_overdue_days <= 30 THEN
        p_dunning_level := 'LEVEL_2';
    ELSIF v_overdue_days <= 60 THEN
        p_dunning_level := 'LEVEL_3';
ELSE
        p_dunning_level := 'LEVEL_4';
END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        p_dunning_level := 'INVOICE_NOT_FOUND';
END;
/

CREATE OR REPLACE PROCEDURE GET_CUSTOMER_DUNNING_SUMMARY (
    p_customer_id IN NUMBER,
    p_unpaid_invoice_count OUT NUMBER,
    p_total_outstanding_amount OUT NUMBER
)
AS
BEGIN
SELECT
    COUNT(*),
    NVL(SUM(amount), 0)
INTO
    p_unpaid_invoice_count,
    p_total_outstanding_amount
FROM invoice
WHERE customer_id = p_customer_id
  AND status = 'UNPAID';
END;
/