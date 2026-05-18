describe('Author Interface', () => {

  it('should create author object', () => {

    const author = {

      id: 1,

      name: 'Zara',

      cpf: '12345678901',

      annualIncome: 50000
    };

    expect(author).toBeTruthy();

  });

});